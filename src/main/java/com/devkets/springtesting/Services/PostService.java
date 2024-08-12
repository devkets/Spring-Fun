package com.devkets.springtesting.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devkets.springtesting.Models.PostRequestModel;
import com.devkets.springtesting.Models.PostResponseModel;
import com.devkets.springtesting.Models.SudokuRequestModel;

@Service
public class PostService {

    final int MAX_VALID = 45;
    
    public PostResponseModel buildPostResponse(PostRequestModel request) {
        
        return null;
    }

    public PostResponseModel carlinsValidationSpecial(SudokuRequestModel request) {
        PostResponseModel response = new PostResponseModel();

        List<String> matrixList = request.getLines();

        boolean rowsValid = false;
        boolean columnsValid = false;
        boolean subMatrixValid = false;

        /**
         *        
        "172549683",
        "645873219",
        "389261745",
        "496327851",
        "813456972",
        "257198436",
        "964715328",
        "731682594",
        "528934167"
         */

        for(String i : matrixList){
            rowsValid = checkValidString(i);
        }

        
        for (int i = 0; i < 9; i++){
            StringBuilder sb = new StringBuilder();
            for (String j : matrixList){
                sb.append(j.charAt(i));
            }
            columnsValid = checkValidString(sb.toString());
        }

        for (int i = 0; i < 3; i++) {
            StringBuilder sb = new StringBuilder();
            for (String j : matrixList){
                sb.append(j.charAt(i));
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

    public boolean checkValidString(String row){
        int sum = 0;
        for(int j = 0; j < row.length(); j++){
            sum += Character.getNumericValue(row.charAt(j));
        }
        if (sum == MAX_VALID) {
            return true;
        }
        return false;
    }

    public PostResponseModel validateSudokuMatrix(SudokuRequestModel request) {
        PostResponseModel response = new PostResponseModel();

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

    public int[][] parseMatrix(List<String> data) {
        
        int[][] matrix = new int[9][9];

        for(int j = 0; j < 9; j++) {
            for(int k = 0; k < 9; k++) {
                matrix[j][k] = Character.getNumericValue(data.get(j).charAt(k));
            }
        }

        return matrix;
    }

    public boolean checkHorizontals(int[][] matrix) {
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

    public boolean checkVerticals(int[][] matrix) {
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

    public boolean checkSubMatrices(int[][] matrix) {
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

    /*
     * (0,0)
     * (3,0)
     * (6,0)
     * (0,3)
     * (3,3)
     * (6,3)
     * (0,6)
     * (3,6)
     * (6,6)
     */
}
