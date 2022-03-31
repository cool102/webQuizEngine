package engine.businesslayer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.*;

public class Solution {

   List<Integer> answer = new ArrayList<>();

    public Solution() {
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public List<Integer> getAnswer() {
        return answer;
    }



    public Solution(List<Integer> answer) {
        this.answer = answer;
    }
}
