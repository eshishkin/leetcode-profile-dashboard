package org.eshishkin.leetcode.model;

import java.util.List;
import lombok.Data;

@Data
public class LeetcodeProfile {
    private String name;
    private String username;

    private Integer solved;
    private Integer submitted;
    private Integer accepted;

    private List<Problem> latestProblems;

    @Data
    public static class Problem {
        private String id;
        private String name;
        private String url;
        private boolean accepted;
        private String info;
    }
}
