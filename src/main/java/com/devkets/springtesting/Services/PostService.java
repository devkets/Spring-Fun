package com.devkets.springtesting.Services;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

import com.devkets.springtesting.Models.SudokuRequestModel;
import com.devkets.springtesting.Models.SudokuResponseModel;

@Service
public class PostService {

    final int MAX_VALID = 45;

    public SudokuResponseModel validateSudokuMatrix(SudokuRequestModel request) {
        SudokuResponseModel response = new SudokuResponseModel();
        response.setRequest(request);

        if(request ==  null) {
            response.setMessage("Input was null.");
            return response;
        }

        List<String> matrixList = request.getLines();

        int[][] sudokuMatrix = parseMatrix(matrixList);

        if(!checkHorizontals(sudokuMatrix)) {
            response.setMessage("Horizontals did not pass inspection.");
            return response;
        }
        if(!checkVerticals(sudokuMatrix)) {
            response.setMessage("Verticals did not pass inspection.");
            return response;
        }
        if(!checkSubMatrices(sudokuMatrix)) {
            response.setMessage("Sub 3x3s did not pass inspection.");
            return response;
        }
        response.setMessage("Valid sudoku puzzle!");
        return response;
    }

    public SudokuResponseModel validateSudokuAllInOne(SudokuRequestModel request) {
        SudokuResponseModel response = new SudokuResponseModel();
        response.setRequest(request);

        List<String> matrixList = request.getLines();
        List<int[]> listOfArrays = new ArrayList<int[]>();
        
        for(int i = 0; i < 9; i++){
            int[] rowArray = new int[9];
            for(int j = 0; j < 9; j++) {
                rowArray[0] = Character.getNumericValue(matrixList.get(i).charAt(j));
            }
            listOfArrays.add(rowArray);
        }


        return response;
    }


    public SudokuResponseModel carlinsValidationSpecial(SudokuRequestModel request) {
        SudokuResponseModel response = new SudokuResponseModel();
        response.setRequest(request);

        List<String> matrixList = request.getLines();

        boolean rowsValid = true;
        boolean columnsValid = true;
        boolean subMatrixValid = true;

       

        for(String i : matrixList){
            if(!checkValidString(i)){
                rowsValid = false;
            }
        }

        
        for (int i = 0; i < 9; i++){
            StringBuilder sb = new StringBuilder();
            for (String j : matrixList){
                sb.append(j.charAt(i));
            }
            if (!checkValidString(sb.toString())){
                columnsValid = false;
            }
        }

        int[] subMatrixSums = new int[9];
        checkSubMatrices(matrixList, subMatrixSums);

        for (int i : subMatrixSums) {
            if (i != 45) {
                subMatrixValid = false;
            }
        }

        if (rowsValid) {
            if (columnsValid) {
                if (subMatrixValid){
                    response.setMessage("Valid puzzle");
                } else {
                    response.setMessage("Sub matrix invalid");
                }
            } else {
                response.setMessage("Columns invalid");
            }
        } else {
            response.setMessage("Rows invalid");
        }
        return response;
    }

    private void checkSubMatrices(List<String> matrixList, int[] subMatrixSums) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++){
                int charValue = Character.getNumericValue(matrixList.get(i).charAt(j));
                if (i < 3) {
                    if (j < 3) {
                        subMatrixSums[0] += charValue;
                    } else if (j < 6) {
                        subMatrixSums[1] += charValue;
                    } else {
                        subMatrixSums[2] += charValue;
                    }
                } else if (i < 6) {
                    if (j < 3) {
                        subMatrixSums[3] += charValue;
                    } else if (j < 6) {
                        subMatrixSums[4] += charValue;
                    } else {
                        subMatrixSums[5] += charValue;
                    }
                } else {
                    if (j < 3) {
                        subMatrixSums[6] += charValue;
                    } else if (j < 6) {
                        subMatrixSums[7] += charValue;
                    } else {
                        subMatrixSums[8] += charValue;
                    }
                }
            }
        }
    }

    private boolean checkValidString(String row){
        int sum = 0;
        for(int j = 0; j < row.length(); j++){
            sum += Character.getNumericValue(row.charAt(j));
        }
        if (sum == MAX_VALID) {
            return true;
        }
        return false;
    }

    private int[][] parseMatrix(List<String> data) {
        
        int[][] matrix = new int[9][9];

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                matrix[i][j] = Character.getNumericValue(data.get(i).charAt(j));
            }
        }

        return matrix;
    }

    private boolean checkHorizontals(int[][] matrix) {
        int horizontalSum;

        for(int j = 0; j < 9; j++) {

            horizontalSum = 0;
            for(int k = 0; k < 9; k++) {
                horizontalSum += matrix[j][k];
            }
            if(horizontalSum != 45) {
                return false;
            }
        }

        return true;
    }

    private boolean checkVerticals(int[][] matrix) {
        int verticalSum;

        for(int j = 0; j < 9; j++) {

            verticalSum = 0;
            for(int k = 0; k < 9; k++) {
                verticalSum += matrix[k][j];
            }
            if(verticalSum != 45) {
                return false;
            }
        }

        return true;
    }

    private boolean checkSubMatrices(int[][] matrix) {
        int subMatrixSum;
        int x = 0;
        int y = 0;

        while(x < 9) {
            subMatrixSum = 0;
            for(int i = x; i < x+3; i++){
                for(int j = y; j < y+3; j++) {
                    subMatrixSum += matrix[i][j];
                }
            }
            if(subMatrixSum != 45) {
                return false;
            }
            x += 3;

            if(x == 9){
                x = 0;
                y += 3;
                if(y == 9){
                    x = 9;
                }
            }
        }

        return true;
    }
}