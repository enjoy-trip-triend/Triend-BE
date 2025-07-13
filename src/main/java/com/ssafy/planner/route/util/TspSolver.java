package com.ssafy.planner.route.util;

import com.ssafy.planner.route.dto.RouteResponseDto;
import com.ssafy.planner.route.dto.WaypointDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TspSolver {
    
    private static final double INF = 1e9;
    
    private static double haversine(WaypointDto a, WaypointDto b) {
        double R = 6371.0;
        double dLat = Math.toRadians(b.getLat() - a.getLat());
        double dLng = Math.toRadians(b.getLng() - a.getLng());
        double lat1 = Math.toRadians(a.getLat());
        double lat2 = Math.toRadians(b.getLat());
        
        double aVal = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(aVal), Math.sqrt(1 - aVal));
        return R * c;
    }
    
    /**
     * 두 지점 간의 거리를 계산하여 2차원 배열로 반환
     */
    private static double[][] buildDist(List<WaypointDto> list) {
        int N = list.size();
        double[][] dist = new double[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dist[i][j] = (i == j) ? 0 : haversine(list.get(i), list.get(j));
            }
        }
        return dist;
    }
    
    public static RouteResponseDto solveTsp(List<WaypointDto> points) {
        int N = points.size();
        int start = 0;
        int end = N - 1;
        
        double[][] dist = buildDist(points);
        double[][] dp = new double[1 << N][N];
        int[][] path = new int[1 << N][N];
        
        for (double[] row : dp) {
            Arrays.fill(row, -1);
        }
        for (int[] row : path) {
            Arrays.fill(row, -1);
        }
        
        // DP + 경로 저장
        solve(1 << start, start, end, dist, dp, path, N);
        
        // 경로 복원
        List<Long> route = new ArrayList<>();
        int visited = 1 << start, current = start;
        route.add(points.get(current)
                .getScheduleId());
        while (path[visited][current] != -1) {
            int next = path[visited][current];
            route.add(points.get(next)
                    .getScheduleId());
            visited |= (1 << next);
            current = next;
        }
        route.add(points.get(end)
                .getScheduleId());
        
        return new RouteResponseDto(route, dp[1 << start][start]);
    }
    
    private static double solve(int visited, int current, int end, double[][] dist, double[][] dp, int[][] path, int N) {
        
        int fullVisited = ((1 << N) - 1) ^ (1 << end); // 목적지는 마지막에만 방문
        
        if (visited == fullVisited) {
            return dist[current][end];
        }
        
        if (dp[visited][current] != -1) {
            return dp[visited][current];
        }
        
        double min = INF;
        
        for (int next = 0; next < N; next++) {
            if ((visited & (1 << next)) != 0 || next == end) {
                continue;
            }
            
            double cost = dist[current][next] + solve(visited | (1 << next), next, end, dist, dp, path, N);
            
            if (cost < min) {
                min = cost;
                path[visited][current] = next;
            }
        }
        
        return dp[visited][current] = min;
    }
}
