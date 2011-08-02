  /**      
 * @{#} MatrixOperator.java Create on 2009-12-24  
 *      
 * Copyright (c) 2009 by tgeosmart.      
 */    
package experiments;

import java.util.ArrayList;

/**  
 * 矩阵操作    
 * @author <a href="mailto:huangliang@tgeosmart.cn">Huang.Liang</a>     
 * @version 1.0      
 */
public class MatrixOperator {
	
	public static final int COMPARE_BIG = 0x0001;
	
	public static final int COMPARE_SMALL = 0x0002;
	
	public static final int COMPARE_EQUA = 0x0004;
	
    /**
     * 创建矩阵
     * @param M 行
     * @param N 列
     * @param dValue 赋值
     * @return 矩阵
     */
	public static Matrix createMatrix(int M, int N, double dValue) {
		Matrix result = new Matrix(M, N);
		double[][] data = result.getData();
		for (int row = 0; row < M; ++row) {
			for (int col = 0; col < N; ++col) {
				data[row][col] = dValue;
			}
		}
		return result;
	}
	/**
	 * 创建矩阵
	 * @param dStart 开始值
	 * @param dEnd
	 * @param bDimRow
	 * @return
	 */
	public static Matrix createMatrix(double dStart, double dEnd, boolean bDimRow) {
		if (dStart > dEnd) {
			return createMatrix(dStart, dEnd, -1, bDimRow);
		}
		else if (dStart < dEnd) {
			return createMatrix(dStart, dEnd, 1, bDimRow);
		} 

		return createMatrix(dStart, dEnd, 0, bDimRow);
	}
	
	public static Matrix createMatrix(double dStart, double dEnd, double dDelta, boolean bDimRow) {
		
		if (dDelta == 0 || (dEnd - dStart)*dDelta < 0) {
			return null;
		}
		
		Matrix result = null;

		int nStep = (int) Math.abs((dEnd - dStart)/dDelta) + 1;
		if (bDimRow) {
			result = new Matrix(1, nStep);
			double[][] data = result.getData();
			for (int col = 0; col < nStep; ++col) {
				data[0][col] = dStart + col * dDelta;
			}
		} else {
			result = new Matrix(nStep, 1);
			double[][] data = result.getData();
			for (int row = 0; row < nStep; ++row) {
				data[row][0] = dStart + row * dDelta;
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param oriMatrix
	 * @param bDimRow 行/列 相乘
	 * @return
	 */
	public static Matrix prod(Matrix oriMatrix, boolean bDimRow) {
		Matrix result = null;
		if (bDimRow) {
			result = new Matrix(1, oriMatrix.getN());
			
			double[][] oriData = oriMatrix.getData();
			double[][] data = result.getData();
			
			for (int col = 0; col < oriMatrix.getN(); ++col) {
				data[0][col] = oriData[0][col];
				for (int row = 1; row < oriMatrix.getM(); ++row) {
					data[0][col] *= oriData[row][col];
				}
			}
		} else {
			result = new Matrix(oriMatrix.getM(), 1);
			
			double[][] oriData = oriMatrix.getData();
			double[][] data = result.getData();
			
			for (int row = 0; row < oriMatrix.getM(); ++row) {
				data[row][0] = oriData[row][0];
				for (int col = 1; col < oriMatrix.getN(); ++col) {
					data[row][0] *= oriData[row][col];
				}
			}
		} 
		
		return result;
	}
	
	/**
	 * 矩阵元素求和
	 * @param m
	 * @return
	 */
	public static Matrix cumsum(Matrix m) {
		Matrix result = null;
		
		int nRow = m.getM();
		int nCol = m.getN();
		
		if (nRow == 1) {
			result = new Matrix(m.getData());
			double[][]data = result.getData();
			
			for (int i = 1; i < nCol; ++i) {
				data[0][i] += data[0][i-1];
			}
		} else if (nRow > 1) {
			result = new Matrix(m.getData());
			double[][]data = result.getData();
			
			for (int i = 1; i < nRow; ++i) {
				for (int j = 0; j < nCol; ++j) {
					data[i][j] += data[i-1][j];
				}
			}
		}
		
		return result;
	}
	
	public static Matrix numAddMatrix(double num, Matrix oriMatrix) {
		Matrix result = new Matrix(oriMatrix.getData());
		double[][] data = result.getData();
		
		for (int row = 0; row < result.getM(); ++row) {
			for (int col = 0; col < result.getN(); ++col) {
				data[row][col] += num;
			}
		}
		return result;
	}
	
	public static Matrix numSubMatrix(double num, Matrix oriMatrix) {
		Matrix result = new Matrix(oriMatrix.getData());
		double[][] data = result.getData();
		
		for (int row = 0; row < result.getM(); ++row) {
			for (int col = 0; col < result.getN(); ++col) {
				data[row][col] = num - data[row][col];
			}
		}
		return result;
	}
	
	public static Matrix numMultiMatrix(double num, Matrix oriMatrix) {
		Matrix result = new Matrix(oriMatrix.getData());
		double[][] data = result.getData();
		
		for (int row = 0; row < result.getM(); ++row) {
			for (int col = 0; col < result.getN(); ++col) {
				data[row][col] *= num;
			}
		}
		return result;
	}

	public static Matrix numDevMatrix(double num, Matrix oriMatrix) {
		if (num == 0) {
			System.out.println("numDevMatrix: Devide Num Can't Be Zero!");
			return null;
		}
		
		Matrix result = new Matrix(oriMatrix.getData());
		double[][] data = result.getData();
		
		for (int row = 0; row < result.getM(); ++row) {
			for (int col = 0; col < result.getN(); ++col) {
				data[row][col] *= num;
			}
		}
		return result;
	}
	
	/**
	 * 按列合并矩阵
	 * @param oriMatrix
	 * @param tarMatrix
	 * @return
	 */
	public static Matrix MergeMatrixColumn(Matrix oriMatrix, Matrix tarMatrix) {
		if (oriMatrix.getM() != tarMatrix.getM()) {
			System.out.println("Merge Matrix Column: Row Num Different!");
			return null;
		}
		Matrix result = new Matrix(oriMatrix.getM(), oriMatrix.getN()+tarMatrix.getN());
		
		int nOriCol = oriMatrix.getN();
		double[][] oriData = oriMatrix.getData();
		double[][] tarData = tarMatrix.getData();
		
		double[][] data = result.getData();
		for (int row = 0; row < result.getM(); ++row) {
			int col = 0;
			for (; col < nOriCol; ++col) {
				data[row][col] = oriData[row][col];
			}
			for (; col < result.getN(); ++col) {
				data[row][col] = tarData[row][col - nOriCol];
			}
		}
		
		return result;
	}
	
	/**
	 * 按行合并矩阵
	 * @param oriMatrix
	 * @param tarMatrix
	 * @return
	 */
	public static Matrix MergeMatrixRow(Matrix oriMatrix, Matrix tarMatrix) {
		if (oriMatrix.getN() != tarMatrix.getN()) {
			System.out.println("Merge Matrix Row: Column Num Different!");
			return null;
		}
		
		Matrix result = new Matrix(oriMatrix.getM() + tarMatrix.getM(), oriMatrix.getN());
		
		int nOriRow = oriMatrix.getM();
		double[][] oriData = oriMatrix.getData();
		double[][] tarData = tarMatrix.getData();
		
		double[][] data = result.getData();
		
		for (int col = 0; col < oriMatrix.getN(); ++col) {
			int row = 0;
			for (; row < nOriRow; ++row) {
				data[row][col] = oriData[row][col];
			}
			for (; row < result.getM(); ++row) {
				data[row][col] = tarData[row - nOriRow][col];
			}
		}
		
		return result;
	}
	
	/**
	 * 获取指定行或列的矩阵
	 * @param oriMatrix
	 * @param arrPos
	 * @param bDimRow
	 * @return
	 */
	public static Matrix GetSubMatrix(Matrix oriMatrix, Integer[] arrPos, boolean bDimRow) {
		int num = arrPos.length;
		Matrix result = null;
		
		if (bDimRow) {
			result = new Matrix(num, oriMatrix.getN());
			double[][] oriData = oriMatrix.getData();
			double[][] data = result.getData();
			for (int i = 0; i < arrPos.length; ++i) {
				int row = arrPos[i];
				for (int col = 0; col < result.getN(); ++col) {
					data[i][col] = oriData[row][col];
				}
			}
		} else {
			result = new Matrix(oriMatrix.getM(), num);
			double[][] oriData = oriMatrix.getData();
			double[][] data = result.getData();
			for (int i = 0; i < arrPos.length; ++i) {
				int col = arrPos[i];
				for (int row = 0; row < result.getM(); ++row) {
					data[row][i] = oriData[row][col];
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 获取矩阵子集
	 * @param oriMatrix
	 * @param arrRow 行序号
	 * @param arrCol列序号
	 * @return
	 */
	public static Matrix GetSubMatrix(Matrix oriMatrix, Integer[] arrRow, Integer[] arrCol) {
		int nRow = 0;
		int nCol = 0;
		
		if (arrRow == null) {
			nRow = oriMatrix.getM();
		} else {
			nRow = arrRow.length;
		}
		
		if (arrCol == null) {
			nCol = oriMatrix.getN();
		} else {
			nCol = arrCol.length;
		}
		
		double[][] oriData = oriMatrix.getData();
		
		Matrix result = null;
		if (arrRow == null) {
			if (arrCol == null) {
				// 返回原始矩阵
				result = new Matrix(oriData);
			} else {
				// 返回指定列所有行
				result = new Matrix(nRow, nCol);
				double[][] data = result.getData();
				for (int i = 0; i < arrCol.length; ++i) {
					int col = arrCol[i];
					for (int row = 0; row < nRow; ++row) {
						data[row][i] = oriData[row][col];
					}
				}
			}
		} else if (arrCol == null) {
			// 返回指定行所有列
			result = new Matrix(nRow, nCol);
			double[][] data = result.getData();
			for (int i = 0; i < arrRow.length; ++i) {
				int row = arrRow[i];
				for (int col = 0; col < nCol; ++col) {
					data[i][col] = oriData[row][col];
				}
			}
		} else {
			// 返回指定行列
			result = new Matrix(nRow, nCol);
			double[][] data = result.getData();
			for (int i = 0; i < arrRow.length; ++i) {
				int row = arrRow[i];
				for (int j = 0; j < arrCol.length; ++j) {
					int col = arrCol[j];
					data[i][j] = oriData[row][col];
				}
			}
		}
		
		
		return result;
	}
	
	/**
	 * 值与矩阵中的值比较
	 * @param oriMatrix
	 * @param dValue
	 * @param nType
	 * @return
	 */
	public static Matrix CompareMatrixAndValue(Matrix oriMatrix, double dValue, int nType) {
	
		Matrix result = new Matrix(oriMatrix.getData());
		double[][] data = result.getData();
		switch (nType) {
		case COMPARE_EQUA:
			for (int row = 0; row < result.getM(); ++row) {
				for (int col = 0; col < result.getN(); ++col) {
					if (data[row][col] == dValue) {
						data[row][col] = 1;
					} else {
						data[row][col] = 0;
					}
				}
			}
			break;
		case COMPARE_BIG:
			for (int row = 0; row < result.getM(); ++row) {
				for (int col = 0; col < result.getN(); ++col) {
					if (data[row][col] > dValue) {
						data[row][col] = 1;
					} else {
						data[row][col] = 0;
					}
				}
			}
			break;
		case COMPARE_SMALL:
			for (int row = 0; row < result.getM(); ++row) {
				for (int col = 0; col < result.getN(); ++col) {
					if (data[row][col] < dValue) {
						data[row][col] = 1;
					} else {
						data[row][col] = 0;
					}
				}
			}
			break;
		default:
			System.out.println("Unsupported Compare Type!");
			break;
		}
		
		return result;
	}
	
	/**
	 * 将矩阵按列优先排列成1列
	 * @param oriMatrix
	 * @return
	 */
	public static Matrix TransMatrixToColumn(Matrix oriMatrix) {
		int rowNum = oriMatrix.getM() * oriMatrix.getN();
		
		Matrix result = new Matrix(rowNum, 1);
		double[][] data = result.getData();
		double[][] oriData = oriMatrix.getData();
		for (int col = 0; col < oriMatrix.getN(); ++col) {
			for (int row = 0; row < oriMatrix.getM(); ++row) {
				data[col * oriMatrix.getM() + row][0] = oriData[row][col];
			}
		}
		
		return result;
	}
	
	/**
	 * 复制矩阵
	 * @param oriMatrix
	 * @param nRepRow
	 * @param nRepCol
	 * @return
	 */
	public static Matrix RepeatMatrix(Matrix oriMatrix, int nRepRow, int nRepCol) {
		
		// 原始矩阵大小
		int nOriRow = oriMatrix.getM();
		int nOriCol = oriMatrix.getN();
		// 生成举证大小
		int nRow = nOriRow * nRepRow;
		int nCol = nOriCol * nRepCol;
		
		Matrix result = new Matrix(nRow, nCol);
		double[][] data = result.getData();
		double[][] oriData = oriMatrix.getData();
		
		for (int row = 0; row < nRepRow; ++row) {
			for (int col = 0; col < nRepCol; ++col) {
				for (int nSubRow = 0; nSubRow < nOriRow; ++nSubRow) {
					int nRowPos = row * nOriRow + nSubRow;
					for (int nSubCol = 0; nSubCol < nOriCol; ++nSubCol) {
						int nColPos = col * nOriCol + nSubCol;
						data[nRowPos][nColPos] = oriData[nSubRow][nSubCol];
					}
				}
			}
		}
		
		return result;
	}
	
	public static Integer[] Sub2ind(Integer[] szMatrix, Integer[][] nPos) {
		
		if (szMatrix.length < 2) {
	        System.out.println("Size vector must have at least 2 elements.");
	        return null;
		}
		
		int nLength = nPos[0].length;
		Integer[] result = new Integer[nLength];
		
		int nDims = Math.min(szMatrix.length, nPos.length);
		
		for (int i = 0; i < nLength; ++i) {
			
			int pos = nPos[0][i];
			for (int j = 1; j < nDims; ++j) {
				int nDimCul = 1;
				for (int k = 0; k < j; ++k){
					nDimCul *= szMatrix[k];
				}
				pos += nDimCul*nPos[j][i];
			}
			result[i] = pos;
		}
		
		return result;
	}
	
	public static double MaxMatrixValue (Matrix m) {
		
		double[][] data = m.getData();
		double dValue = data[0][0];
		
		for (int row = 0; row < m.getM(); ++row) {
			for (int col = 0; col < m.getN(); ++col) {
				dValue = Math.max(dValue, data[row][col]);
			}
		}
		return dValue;
	}
	
	public static double MinMatrixValue (Matrix m) {
		
		double[][] data = m.getData();
		double dValue = data[0][0];
		
		for (int row = 0; row < m.getM(); ++row) {
			for (int col = 0; col < m.getN(); ++col) {
				dValue = Math.min(dValue, data[row][col]);
			}
		}
		return dValue;
	}
	
	public static Matrix GetSubMatrixFromPos(Matrix oriMatrix, Integer[] nPos) {
		
		Matrix result = new Matrix(1, nPos.length);
		double[][] data = result.getData();
		double[][] oriData = oriMatrix.getData();
		
		for (int i = 0; i < nPos.length; ++i) {
			int pos = nPos[i];
			int row = pos%oriMatrix.getM();
			int col = (int) pos/oriMatrix.getM();
			
			data[0][i] = oriData[row][col];
		}
		
		return result;
	}
}
