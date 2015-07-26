package com.bn.box2d.bheap;

public class ColorUtil 
{
   static int[][] result= 
   {
	   {56,225,254},   
	   {41,246,239},
	   {34,244,197},
	   {44,241,161},
	   {65,239,106},
	   {45,238,59},
	   {73,244,51},   
	   {99,233,58},
	   {129,243,34},
	   {142,245,44},
	   {187,243,32},
	   {232,250,28},
	   {242,230,46},
	   {248,196,51},
	   {244,125,31},
	   {247,88,46},
	   {249,70,40},
	   {249,70,40},
	   {248,48,48},
	   {250,30,30},
	   {252,15,15},
	   {255,0,0}, 
   };
	
   public static int getColor(int index)
   {
	   int[] rgb=result[index%result.length];
	   int result=0xff000000;
	   result=result|(rgb[0]<<16);
	   result=result|(rgb[1]<<8);
	   result=result|(rgb[2]);
	   return result;
   }
	
}
