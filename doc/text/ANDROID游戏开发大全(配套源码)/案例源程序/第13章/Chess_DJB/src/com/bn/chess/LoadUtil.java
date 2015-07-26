package com.bn.chess;
import static com.bn.chess.Constant.*;
import static com.bn.chess.Chess_LoadUtil.*;

import java.util.Arrays;

public class LoadUtil {
	public static int sdPlayer=1;                   // 轮到谁走，0=红方，1=黑方
	public static int ucpcSquares[]=new int[256];          // 棋盘上的棋子
	public static int vlWhite, vlBlack;           // 红、黑双方的子力价值
	public static int nDistance;                  // 距离根节点的步数	
	public static int mvResult;             // 电脑走的棋
	public static int nHistoryTable[]=new int[65536]; // 历史表
	
	public static void initHistorytable()
	{
		for(int i=0;i<nHistoryTable.length;i++)
		{
			nHistoryTable[i]=0;
		}
	}
		public static void ChangeSide() {         // 交换走子方
		    sdPlayer = 1 - sdPlayer;
		}
		public static void AddPiece(int sq, int pc) { // 在棋盘上放一枚棋子
		    ucpcSquares[sq] = pc;
		    // 红方加分，黑方(注意"cucvlPiecePos"取值要颠倒)减分
		    if (pc < 16) {
		      vlWhite += cucvlPiecePos[pc - 8][sq];
		    } else {
		      vlBlack += cucvlPiecePos[pc - 16][SQUARE_FLIP(sq)];
		    }
		}
		public static void DelPiece(int sq, int pc) { // 从棋盘上拿走一枚棋子
		    ucpcSquares[sq] = 0;
		    // 红方减分，黑方(注意"cucvlPiecePos"取值要颠倒)加分
		    if (pc < 16) {
			    vlWhite -= cucvlPiecePos[pc - 8][sq];
			} else {
			    vlBlack -= cucvlPiecePos[pc - 16][SQUARE_FLIP(sq)];
		    }
		}
		public static int Evaluate()  {      // 局面评价函数
		  return (sdPlayer == 0 ? vlWhite - vlBlack : vlBlack - vlWhite) + ADVANCED_VALUE;
		}
		public static void UndoMakeMove(int mv, int pcCaptured) { // 撤消走一步棋
		    nDistance --;
		    ChangeSide();//交换走子
		    UndoMovePiece(mv, pcCaptured);//撤销走子
		}
	
	// 初始化棋盘
	public static void Startup() {
	  int sq, pc;
	  sdPlayer = vlWhite = vlBlack = nDistance = 0;
	 for(int i=0;i<256;i++)//初始化为零
	 {
		 ucpcSquares[i]=0;
	 }
	  for (sq = 0; sq < 256; sq ++) {
	    pc = cucpcStartup[sq];  
	    if (pc != 0) {
	      AddPiece(sq, pc);
	      
	    }
	  }
	}

	// 搬一步棋的棋子
	public static int MovePiece(int mv) {
	  int sqSrc, sqDst, pc, pcCaptured;
	  sqSrc = SRC(mv);
	  sqDst = DST(mv);
	  pcCaptured = ucpcSquares[sqDst];//得到目的格子的棋子
	  if (pcCaptured != 0) {//目的地不为空
	    DelPiece(sqDst, pcCaptured);//删掉目标格子棋子
	  }
	  pc = ucpcSquares[sqSrc];//得到初始格子上的棋子
	  DelPiece(sqSrc, pc);//删掉初始格子上的棋子
	  AddPiece(sqDst, pc);//在目标格子上放上棋子
	  return pcCaptured;//返回原来目标格子上的棋子
	}

	// 撤消搬一步棋的棋子
	public static void UndoMovePiece(int mv, int pcCaptured) {
	  int sqSrc, sqDst, pc;
	  sqSrc = SRC(mv);
	  sqDst = DST(mv);
	  pc = ucpcSquares[sqDst];
	  DelPiece(sqDst, pc);
	  AddPiece(sqSrc, pc);
	  if (pcCaptured != 0) {
	    AddPiece(sqDst, pcCaptured);
	  }}
	// 走一步棋
	public static boolean MakeMove(int mv, int pcCaptured) {
	  pcCaptured = MovePiece(mv);  
	  if (Checked()) {
	    UndoMovePiece(mv, pcCaptured);
	    return false;
	  }
	  ChangeSide();
	  nDistance ++;
	  return true;
	}
	
	// 生成所有走法
	public static int GenerateMoves(Integer[] mvs)  {
	  int i, j, nGenMoves, nDelta, sqSrc, sqDst;
	  int pcSelfSide, pcOppSide, pcSrc, pcDst;
	  // 生成所有走法，需要经过以下几个步骤：

	  nGenMoves = 0;
	  pcSelfSide = SIDE_TAG(sdPlayer);
	  pcOppSide = OPP_SIDE_TAG(sdPlayer);
	  for (sqSrc = 0; sqSrc < 256; sqSrc ++) {

	    // 1. 找到一个本方棋子，再做以下判断：
	    pcSrc = ucpcSquares[sqSrc];
	    if ((pcSrc & pcSelfSide) == 0) {
	      continue;
	    }

	    // 2. 根据棋子确定走法
	    switch (pcSrc - pcSelfSide) {
	    case PIECE_KING:
	      for (i = 0; i < 4; i ++) {
	        sqDst = sqSrc + ccKingDelta[i];
	        if (!IN_FORT(sqDst)) {
	          continue;
	        }
	        pcDst = ucpcSquares[sqDst];
	        if ((pcDst & pcSelfSide) == 0) {
	          mvs[nGenMoves] = MOVE(sqSrc, sqDst);// 根据起点和终点获得走法
	          nGenMoves ++;
	        }
	      }
	      break;
	    case PIECE_ADVISOR:
	      for (i = 0; i < 4; i ++) {
	        sqDst = sqSrc + ccAdvisorDelta[i];  
	        if (!IN_FORT(sqDst)) {
	          continue;
	        }
	        pcDst = ucpcSquares[sqDst];
	        if ((pcDst & pcSelfSide) == 0) {
	          mvs[nGenMoves] = MOVE(sqSrc, sqDst);
	          nGenMoves ++;
	        }
	      }
	      break;
	    case PIECE_BISHOP:
	      for (i = 0; i < 4; i ++) {
	        sqDst = sqSrc + ccAdvisorDelta[i];  
	        if (!(IN_BOARD(sqDst) && HOME_HALF(sqDst, sdPlayer) && ucpcSquares[sqDst] == 0)) {
	          continue;
	        }
	        sqDst += ccAdvisorDelta[i];  
	        pcDst = ucpcSquares[sqDst];
	        if ((pcDst & pcSelfSide) == 0) {
	          mvs[nGenMoves] = MOVE(sqSrc, sqDst);
	          nGenMoves ++;
	        }
	      }
	      break;
	    case PIECE_KNIGHT:
	      for (i = 0; i < 4; i ++) {
	        sqDst = sqSrc + ccKingDelta[i];  
	        if (ucpcSquares[sqDst] != 0) {
	          continue;
	        }
	        for (j = 0; j < 2; j ++) {
	          sqDst = sqSrc + ccKnightDelta[i][j];
	          if (!IN_BOARD(sqDst)) {  
	            continue;
	          }
	          pcDst = ucpcSquares[sqDst];
	          if ((pcDst & pcSelfSide) == 0) {
	            mvs[nGenMoves] = MOVE(sqSrc, sqDst);
	            nGenMoves ++;
	          }
	        }
	      }
	      break;
	    case PIECE_ROOK:
	      for (i = 0; i < 4; i ++) {
	        nDelta = ccKingDelta[i];  
	        sqDst = sqSrc + nDelta;
	        while (IN_BOARD(sqDst)) {
	          pcDst = ucpcSquares[sqDst];
	          if (pcDst == 0) {
	              mvs[nGenMoves] = MOVE(sqSrc, sqDst);
	              nGenMoves ++;
	          } else {
	            if ((pcDst & pcOppSide) != 0) {
	              mvs[nGenMoves] = MOVE(sqSrc, sqDst);
	              nGenMoves ++;
	            }
	            break;
	          }
	          sqDst += nDelta;
	        }
	      }
	      break;
	    case PIECE_CANNON:
	      for (i = 0; i < 4; i ++) {
	        nDelta = ccKingDelta[i];
	        sqDst = sqSrc + nDelta;
	        while (IN_BOARD(sqDst)) {
	          pcDst = ucpcSquares[sqDst];
	          if (pcDst == 0) {
	            mvs[nGenMoves] = MOVE(sqSrc, sqDst);
	            nGenMoves ++;
	          } else {
	            break;
	          }
	          sqDst += nDelta;
	        }
	        sqDst += nDelta;
	        while (IN_BOARD(sqDst)) {
	          pcDst = ucpcSquares[sqDst];
	          if (pcDst != 0) {
	            if ((pcDst & pcOppSide) != 0) {
	              mvs[nGenMoves] = MOVE(sqSrc, sqDst);
	              nGenMoves ++;
	            }
	            break;
	          }
	          sqDst += nDelta;
	        }
	      }
	      break;
	    case PIECE_PAWN:
	      sqDst = SQUARE_FORWARD(sqSrc, sdPlayer);// 格子水平镜像
	      if (IN_BOARD(sqDst)) {// 判断棋子是否在棋盘中
	        pcDst = ucpcSquares[sqDst];
	        if ((pcDst & pcSelfSide) == 0) {
	          mvs[nGenMoves] = MOVE(sqSrc, sqDst);
	          nGenMoves ++;
	        }
	      }
	      if (AWAY_HALF(sqSrc, sdPlayer)) {
	        for (nDelta = -1; nDelta <= 1; nDelta += 2) {
	          sqDst = sqSrc + nDelta;
	          if (IN_BOARD(sqDst)) {
	            pcDst = ucpcSquares[sqDst];
	            if ((pcDst & pcSelfSide) == 0) {
	              mvs[nGenMoves] = MOVE(sqSrc, sqDst);
	              nGenMoves ++;
	            }
	          }
	        }
	      }
	      break;
	    }
	  }
	  return nGenMoves;
	}

	// 判断走法是否合理
	public static boolean LegalMove(int mv)  {
	  int sqSrc, sqDst, sqPin;
	  int pcSelfSide, pcSrc, pcDst, nDelta;
	  // 判断走法是否合法，需要经过以下的判断过程：

	  // 1. 判断起始格是否有自己的棋子
	  sqSrc = SRC(mv);
	  pcSrc = ucpcSquares[sqSrc];
	  pcSelfSide = SIDE_TAG(sdPlayer);  // 获得红黑标记(红子是8，黑子是16)
	  if ((pcSrc & pcSelfSide) == 0) {
	    return false;
	  }

	  // 2. 判断目标格是否有自己的棋子
	  sqDst = DST(mv);
	  pcDst = ucpcSquares[sqDst];
	  if ((pcDst & pcSelfSide) != 0) {
	    return false;
	  }

	  // 3. 根据棋子的类型检查走法是否合理
	  switch (pcSrc - pcSelfSide) {
	  case PIECE_KING:
	    return IN_FORT(sqDst) && KING_SPAN(sqSrc, sqDst);  
	  case PIECE_ADVISOR:
	    return IN_FORT(sqDst) && ADVISOR_SPAN(sqSrc, sqDst);
	  case PIECE_BISHOP:
	    return SAME_HALF(sqSrc, sqDst) && BISHOP_SPAN(sqSrc, sqDst) &&
	        ucpcSquares[BISHOP_PIN(sqSrc, sqDst)] == 0;
	  case PIECE_KNIGHT:
	    sqPin = KNIGHT_PIN(sqSrc, sqDst);
	    return sqPin != sqSrc && ucpcSquares[sqPin] == 0;
	  case PIECE_ROOK:
	  case PIECE_CANNON:
	    if (SAME_RANK(sqSrc, sqDst)) {
	      nDelta = (sqDst < sqSrc ? -1 : 1);
	    } else if (SAME_FILE(sqSrc, sqDst)) {
	      nDelta = (sqDst < sqSrc ? -16 : 16);
	    } else {
	      return false;
	    }
	    sqPin = sqSrc + nDelta;
	    while (sqPin != sqDst && ucpcSquares[sqPin] == 0) {
	      sqPin += nDelta;
	    }
	    if (sqPin == sqDst) {
	      return pcDst == 0 || pcSrc - pcSelfSide == PIECE_ROOK;
	    } else if (pcDst != 0 && pcSrc - pcSelfSide == PIECE_CANNON) {
	      sqPin += nDelta;
	      while (sqPin != sqDst && ucpcSquares[sqPin] == 0) {
	        sqPin += nDelta;
	      }
	      return sqPin == sqDst;
	    } else {
	      return false;
	    }
	  case PIECE_PAWN:
	    if (AWAY_HALF(sqDst, sdPlayer) && (sqDst == sqSrc - 1 || sqDst == sqSrc + 1)) {
	      return true;
	    }
	    return sqDst == SQUARE_FORWARD(sqSrc, sdPlayer);
	  default:
	    return false;
	  }
	}

	// 判断是否被将军
	  public static boolean Checked()  {
	  int i, j, sqSrc, sqDst;
	  int pcSelfSide, pcOppSide, pcDst, nDelta;
	  pcSelfSide = SIDE_TAG(sdPlayer);// 获得红黑标记(红子是8，黑子是16)
	  pcOppSide = OPP_SIDE_TAG(sdPlayer);// 获得红黑标记，对方的
	  // 找到棋盘上的帅(将)，再做以下判断：

	  for (sqSrc = 0; sqSrc < 256; sqSrc ++) {
	    if (ucpcSquares[sqSrc] != pcSelfSide + PIECE_KING) {
	      continue;
	    }

	    // 1. 判断是否被对方的兵(卒)将军
	    if (ucpcSquares[SQUARE_FORWARD(sqSrc, sdPlayer)] == pcOppSide + PIECE_PAWN) {
	      return true;
	    }
	    for (nDelta = -1; nDelta <= 1; nDelta += 2) {
	      if (ucpcSquares[sqSrc + nDelta] == pcOppSide + PIECE_PAWN) {
	        return true;
	      }
	    }

	    // 2. 判断是否被对方的马将军(以仕(士)的步长当作马腿)
	    for (i = 0; i < 4; i ++) {
	      if (ucpcSquares[sqSrc + ccAdvisorDelta[i]] != 0) { 
	        continue;
	      }
	      for (j = 0; j < 2; j ++) {
	        int pcDstt = ucpcSquares[sqSrc + ccKnightCheckDelta[i][j]];
	        if (pcDstt == pcOppSide + PIECE_KNIGHT) {
	          return true;
	        }
	      }
	    }

	    // 3. 判断是否被对方的车或炮将军(包括将帅对脸)
	    for (i = 0; i < 4; i ++) {
	      nDelta = ccKingDelta[i];
	      sqDst = sqSrc + nDelta;
	      while (IN_BOARD(sqDst)) {
	        pcDst = ucpcSquares[sqDst];
	        if (pcDst != 0) {
	          if (pcDst == pcOppSide + PIECE_ROOK || pcDst == pcOppSide + PIECE_KING) {
	            return true;
	          }
	          break;
	        }
	        sqDst += nDelta;
	      }
	      sqDst += nDelta;
	      while (IN_BOARD(sqDst)) {
	         pcDst = ucpcSquares[sqDst];  
	        if (pcDst != 0) {
	          if (pcDst == pcOppSide + PIECE_CANNON) {
	            return true;
	          }
	          break;
	        }
	        sqDst += nDelta;
	      }
	    }
	    return false;
	  }
	  return false;
	}

	// 判断是否被杀
	public static boolean IsMate() {
	  int i, nGenMoveNum, pcCaptured ;
	  Integer mvs[]=new Integer[MAX_GEN_MOVES];

	  nGenMoveNum = GenerateMoves(mvs);
	  for (i = 0; i < nGenMoveNum; i ++) {
		  pcCaptured = MovePiece(mvs[i]);
	    if (!Checked()) {
	      UndoMovePiece(mvs[i], pcCaptured);
	      return false;
	    } else {
	      UndoMovePiece(mvs[i], pcCaptured);
	    }
	  }
	  return true;
	}
	

	// 超出边界(Fail-Soft)的Alpha-Beta搜索过程
	public static int SearchFull(int vlAlpha, int vlBeta, int nDepth) {
	  int i=0, nGenMoves=0,pcCaptured=0;
	  int vl, vlBest, mvBest;
	  Integer mvs[]=new Integer[MAX_GEN_MOVES];
	  // 一个Alpha-Beta完全搜索分为以下几个阶段

	  // 1. 到达水平线，则返回局面评价值
	  if (nDepth == 0) {
	    return Evaluate();
	  }

	  // 2. 初始化最佳值和最佳走法
	  vlBest = -MATE_VALUE; // 这样可以知道，是否一个走法都没走过(杀棋)
	  mvBest = 0;           // 这样可以知道，是否搜索到了Beta走法或PV走法，以便保存到历史表

	  // 3. 生成全部走法，并根据历史表排序
	  nGenMoves = GenerateMoves(mvs);
	  Arrays.sort(mvs, 0,nGenMoves,new MyComparator());

	  // 4. 逐一走这些走法，并进行递归
	  for (i = 0; i < nGenMoves; i ++) {    
		  pcCaptured=ucpcSquares[DST( mvs[i])];
	    if (MakeMove(mvs[i], pcCaptured)) {
	      vl = -SearchFull(-vlBeta, -vlAlpha, nDepth - 1);
	      UndoMakeMove(mvs[i], pcCaptured);

	      // 5. 进行Alpha-Beta大小判断和截断
	      if (vl > vlBest) {    // 找到最佳值(但不能确定是Alpha、PV还是Beta走法)
	        vlBest = vl;        // "vlBest"就是目前要返回的最佳值，可能超出Alpha-Beta边界
	        if (vl >= vlBeta) { // 找到一个Beta走法
	          mvBest = mvs[i];  // Beta走法要保存到历史表
	          break;            // Beta截断
	        }
	        if (vl > vlAlpha) { // 找到一个PV走法
	          mvBest = mvs[i];  // PV走法要保存到历史表
	          vlAlpha = vl;     // 缩小Alpha-Beta边界
	        }
	      }
	    }
	  }

	  // 5. 所有走法都搜索完了，把最佳走法(不能是Alpha走法)保存到历史表，返回最佳值
	  if (vlBest == -MATE_VALUE) {
	    // 如果是杀棋，就根据杀棋步数给出评价
	    return nDistance - MATE_VALUE;
	  }
	  if (mvBest != 0) {
	    // 如果不是Alpha走法，就将最佳走法保存到历史表
		  nHistoryTable[mvBest] += nDepth * nDepth;
	    if (nDistance == 0) {
	      // 搜索根节点时，总是有一个最佳走法(因为全窗口搜索不会超出边界)，将这个走法保存下来
	      mvResult = mvBest;
	    }
	  }
	  return vlBest;
	}

	// 迭代加深搜索过程
	public static void SearchMain() {
	  int i, vl;

	  // 初始化
	  initHistorytable();
	  nDistance = 0; // 初始步数
	  long start=System.nanoTime();
	  // 迭代加深过程
	  for (i = 1; i <= LIMIT_DEPTH; i ++) {   
	    vl = SearchFull(-MATE_VALUE, MATE_VALUE, i);  
	    // 搜索到杀棋，就终止搜索
	    if (vl > WIN_VALUE || vl < -WIN_VALUE) {
	      break;
	    }
	    // 超过时间，就终止搜索
	    if((System.nanoTime()-start)/1.e9>ViewConstant.thinkDeeplyTime)
	    break;
	  }
	}	
}
