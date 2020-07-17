package org.eshishkin.leetcode.model;

import java.util.List;
import lombok.Data;

@Data
public class LeetcodeProfile {
    private String name;
    private String username;

    private int solved;
    private int submitted;
    private int accepted;

    private List<Problem> latestProblems;

    @Data
    public static class Problem {
        private String name;
        private String url;
        private boolean accepted;
        private String info;
    }
}
