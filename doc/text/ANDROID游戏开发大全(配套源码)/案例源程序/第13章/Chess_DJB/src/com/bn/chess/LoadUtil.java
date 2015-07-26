package com.bn.chess;
import static com.bn.chess.Constant.*;
import static com.bn.chess.Chess_LoadUtil.*;

import java.util.Arrays;

public class LoadUtil {
	public static int sdPlayer=1;                   // �ֵ�˭�ߣ�0=�췽��1=�ڷ�
	public static int ucpcSquares[]=new int[256];          // �����ϵ�����
	public static int vlWhite, vlBlack;           // �졢��˫����������ֵ
	public static int nDistance;                  // ������ڵ�Ĳ���	
	public static int mvResult;             // �����ߵ���
	public static int nHistoryTable[]=new int[65536]; // ��ʷ��
	
	public static void initHistorytable()
	{
		for(int i=0;i<nHistoryTable.length;i++)
		{
			nHistoryTable[i]=0;
		}
	}
		public static void ChangeSide() {         // �������ӷ�
		    sdPlayer = 1 - sdPlayer;
		}
		public static void AddPiece(int sq, int pc) { // �������Ϸ�һö����
		    ucpcSquares[sq] = pc;
		    // �췽�ӷ֣��ڷ�(ע��"cucvlPiecePos"ȡֵҪ�ߵ�)����
		    if (pc < 16) {
		      vlWhite += cucvlPiecePos[pc - 8][sq];
		    } else {
		      vlBlack += cucvlPiecePos[pc - 16][SQUARE_FLIP(sq)];
		    }
		}
		public static void DelPiece(int sq, int pc) { // ������������һö����
		    ucpcSquares[sq] = 0;
		    // �췽���֣��ڷ�(ע��"cucvlPiecePos"ȡֵҪ�ߵ�)�ӷ�
		    if (pc < 16) {
			    vlWhite -= cucvlPiecePos[pc - 8][sq];
			} else {
			    vlBlack -= cucvlPiecePos[pc - 16][SQUARE_FLIP(sq)];
		    }
		}
		public static int Evaluate()  {      // �������ۺ���
		  return (sdPlayer == 0 ? vlWhite - vlBlack : vlBlack - vlWhite) + ADVANCED_VALUE;
		}
		public static void UndoMakeMove(int mv, int pcCaptured) { // ������һ����
		    nDistance --;
		    ChangeSide();//��������
		    UndoMovePiece(mv, pcCaptured);//��������
		}
	
	// ��ʼ������
	public static void Startup() {
	  int sq, pc;
	  sdPlayer = vlWhite = vlBlack = nDistance = 0;
	 for(int i=0;i<256;i++)//��ʼ��Ϊ��
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

	// ��һ���������
	public static int MovePiece(int mv) {
	  int sqSrc, sqDst, pc, pcCaptured;
	  sqSrc = SRC(mv);
	  sqDst = DST(mv);
	  pcCaptured = ucpcSquares[sqDst];//�õ�Ŀ�ĸ��ӵ�����
	  if (pcCaptured != 0) {//Ŀ�ĵز�Ϊ��
	    DelPiece(sqDst, pcCaptured);//ɾ��Ŀ���������
	  }
	  pc = ucpcSquares[sqSrc];//�õ���ʼ�����ϵ�����
	  DelPiece(sqSrc, pc);//ɾ����ʼ�����ϵ�����
	  AddPiece(sqDst, pc);//��Ŀ������Ϸ�������
	  return pcCaptured;//����ԭ��Ŀ������ϵ�����
	}

	// ������һ���������
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
	// ��һ����
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
	
	// ���������߷�
	public static int GenerateMoves(Integer[] mvs)  {
	  int i, j, nGenMoves, nDelta, sqSrc, sqDst;
	  int pcSelfSide, pcOppSide, pcSrc, pcDst;
	  // ���������߷�����Ҫ�������¼������裺

	  nGenMoves = 0;
	  pcSelfSide = SIDE_TAG(sdPlayer);
	  pcOppSide = OPP_SIDE_TAG(sdPlayer);
	  for (sqSrc = 0; sqSrc < 256; sqSrc ++) {

	    // 1. �ҵ�һ���������ӣ����������жϣ�
	    pcSrc = ucpcSquares[sqSrc];
	    if ((pcSrc & pcSelfSide) == 0) {
	      continue;
	    }

	    // 2. ��������ȷ���߷�
	    switch (pcSrc - pcSelfSide) {
	    case PIECE_KING:
	      for (i = 0; i < 4; i ++) {
	        sqDst = sqSrc + ccKingDelta[i];
	        if (!IN_FORT(sqDst)) {
	          continue;
	        }
	        pcDst = ucpcSquares[sqDst];
	        if ((pcDst & pcSelfSide) == 0) {
	          mvs[nGenMoves] = MOVE(sqSrc, sqDst);// ���������յ����߷�
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
	      sqDst = SQUARE_FORWARD(sqSrc, sdPlayer);// ����ˮƽ����
	      if (IN_BOARD(sqDst)) {// �ж������Ƿ���������
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

	// �ж��߷��Ƿ����
	public static boolean LegalMove(int mv)  {
	  int sqSrc, sqDst, sqPin;
	  int pcSelfSide, pcSrc, pcDst, nDelta;
	  // �ж��߷��Ƿ�Ϸ�����Ҫ�������µ��жϹ��̣�

	  // 1. �ж���ʼ���Ƿ����Լ�������
	  sqSrc = SRC(mv);
	  pcSrc = ucpcSquares[sqSrc];
	  pcSelfSide = SIDE_TAG(sdPlayer);  // ��ú�ڱ��(������8��������16)
	  if ((pcSrc & pcSelfSide) == 0) {
	    return false;
	  }

	  // 2. �ж�Ŀ����Ƿ����Լ�������
	  sqDst = DST(mv);
	  pcDst = ucpcSquares[sqDst];
	  if ((pcDst & pcSelfSide) != 0) {
	    return false;
	  }

	  // 3. �������ӵ����ͼ���߷��Ƿ����
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

	// �ж��Ƿ񱻽���
	  public static boolean Checked()  {
	  int i, j, sqSrc, sqDst;
	  int pcSelfSide, pcOppSide, pcDst, nDelta;
	  pcSelfSide = SIDE_TAG(sdPlayer);// ��ú�ڱ��(������8��������16)
	  pcOppSide = OPP_SIDE_TAG(sdPlayer);// ��ú�ڱ�ǣ��Է���
	  // �ҵ������ϵ�˧(��)�����������жϣ�

	  for (sqSrc = 0; sqSrc < 256; sqSrc ++) {
	    if (ucpcSquares[sqSrc] != pcSelfSide + PIECE_KING) {
	      continue;
	    }

	    // 1. �ж��Ƿ񱻶Է��ı�(��)����
	    if (ucpcSquares[SQUARE_FORWARD(sqSrc, sdPlayer)] == pcOppSide + PIECE_PAWN) {
	      return true;
	    }
	    for (nDelta = -1; nDelta <= 1; nDelta += 2) {
	      if (ucpcSquares[sqSrc + nDelta] == pcOppSide + PIECE_PAWN) {
	        return true;
	      }
	    }

	    // 2. �ж��Ƿ񱻶Է�������(����(ʿ)�Ĳ�����������)
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

	    // 3. �ж��Ƿ񱻶Է��ĳ����ڽ���(������˧����)
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

	// �ж��Ƿ�ɱ
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
	

	// �����߽�(Fail-Soft)��Alpha-Beta��������
	public static int SearchFull(int vlAlpha, int vlBeta, int nDepth) {
	  int i=0, nGenMoves=0,pcCaptured=0;
	  int vl, vlBest, mvBest;
	  Integer mvs[]=new Integer[MAX_GEN_MOVES];
	  // һ��Alpha-Beta��ȫ������Ϊ���¼����׶�

	  // 1. ����ˮƽ�ߣ��򷵻ؾ�������ֵ
	  if (nDepth == 0) {
	    return Evaluate();
	  }

	  // 2. ��ʼ�����ֵ������߷�
	  vlBest = -MATE_VALUE; // ��������֪�����Ƿ�һ���߷���û�߹�(ɱ��)
	  mvBest = 0;           // ��������֪�����Ƿ���������Beta�߷���PV�߷����Ա㱣�浽��ʷ��

	  // 3. ����ȫ���߷�����������ʷ������
	  nGenMoves = GenerateMoves(mvs);
	  Arrays.sort(mvs, 0,nGenMoves,new MyComparator());

	  // 4. ��һ����Щ�߷��������еݹ�
	  for (i = 0; i < nGenMoves; i ++) {    
		  pcCaptured=ucpcSquares[DST( mvs[i])];
	    if (MakeMove(mvs[i], pcCaptured)) {
	      vl = -SearchFull(-vlBeta, -vlAlpha, nDepth - 1);
	      UndoMakeMove(mvs[i], pcCaptured);

	      // 5. ����Alpha-Beta��С�жϺͽض�
	      if (vl > vlBest) {    // �ҵ����ֵ(������ȷ����Alpha��PV����Beta�߷�)
	        vlBest = vl;        // "vlBest"����ĿǰҪ���ص����ֵ�����ܳ���Alpha-Beta�߽�
	        if (vl >= vlBeta) { // �ҵ�һ��Beta�߷�
	          mvBest = mvs[i];  // Beta�߷�Ҫ���浽��ʷ��
	          break;            // Beta�ض�
	        }
	        if (vl > vlAlpha) { // �ҵ�һ��PV�߷�
	          mvBest = mvs[i];  // PV�߷�Ҫ���浽��ʷ��
	          vlAlpha = vl;     // ��СAlpha-Beta�߽�
	        }
	      }
	    }
	  }

	  // 5. �����߷����������ˣ�������߷�(������Alpha�߷�)���浽��ʷ���������ֵ
	  if (vlBest == -MATE_VALUE) {
	    // �����ɱ�壬�͸���ɱ�岽����������
	    return nDistance - MATE_VALUE;
	  }
	  if (mvBest != 0) {
	    // �������Alpha�߷����ͽ�����߷����浽��ʷ��
		  nHistoryTable[mvBest] += nDepth * nDepth;
	    if (nDistance == 0) {
	      // �������ڵ�ʱ��������һ������߷�(��Ϊȫ�����������ᳬ���߽�)��������߷���������
	      mvResult = mvBest;
	    }
	  }
	  return vlBest;
	}

	// ����������������
	public static void SearchMain() {
	  int i, vl;

	  // ��ʼ��
	  initHistorytable();
	  nDistance = 0; // ��ʼ����
	  long start=System.nanoTime();
	  // �����������
	  for (i = 1; i <= LIMIT_DEPTH; i ++) {   
	    vl = SearchFull(-MATE_VALUE, MATE_VALUE, i);  
	    // ������ɱ�壬����ֹ����
	    if (vl > WIN_VALUE || vl < -WIN_VALUE) {
	      break;
	    }
	    // ����ʱ�䣬����ֹ����
	    if((System.nanoTime()-start)/1.e9>ViewConstant.thinkDeeplyTime)
	    break;
	  }
	}	
}
