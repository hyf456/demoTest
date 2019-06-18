package com.han.search.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: hanyf
 * @Description: 广度优先搜索
 * @Date: 2018/10/31 14:57
 */
public class BreadthFirstSearch {

    //定义 M N
    private int M = 0;
    private int N = 0;

    //定义方向常量
    private static final int RIGHT = 1;
    private static final int DOWN = 2;

    //代表广搜的每一步，通过lastItem链起来
    private class SearchItem {
        private int curX;
        private int curY;
        private int dir;
        //这里记录(curX, curY)路径的最大值，相当于best(i,j)
        private int value;
        private SearchItem lastItem;

        public SearchItem(int curX, int curY, int dir, int value) {
            this.curX = curX;
            this.curY = curY;
            this.dir = dir;
            this.value = value;
        }
    }

    //最终搜到的结果
    private SearchItem bestItem = null;

    //广搜的存储队列
    private List<SearchItem> statusToSearch = new LinkedList<SearchItem>();

    //替换广搜队列中较小的item，返回值为搜索队列中是否找到对应的item
    private boolean replaceStatusList(SearchItem newItem) {
        //是否找到
        boolean find = false;
        //遍历查找
        for (int i = 0; i < statusToSearch.size(); i++) {
            SearchItem searchItem = statusToSearch.get(i);
            //找到对应的item
            if (searchItem.curX == newItem.curX && searchItem.curY == newItem.curY) {
                find = true;
                //这里相当于在对比best(i,j)
                if (searchItem.value < newItem.value) {
                    //替换成更好的item
                    statusToSearch.remove(i);
                    statusToSearch.add(i, newItem);
                }
                break;
            }
        }
        return find;
    }

    //广搜
    private void search(int[][] matrix) {
// 搜索队列中不为空
        while(statusToSearch.size() > 0) {
            // 从搜索队列中获取一个item
            SearchItem searchItem = statusToSearch.remove(0);
            int curX = searchItem.curX;
            int curY = searchItem.curY;
            int curValue = searchItem.value;
            // 如果已经搜到
            if(curX == M - 1 && curY == N - 1) {
                bestItem = searchItem;
            }
            // 可往下搜
            if(curX < M - 1) {
                SearchItem newItem = new SearchItem(curX + 1, curY, DOWN, curValue + matrix[curX + 1][curY]);
                newItem.lastItem = searchItem;
                // 是否替代
                if(!replaceStatusList(newItem)) {
                    // 搜索队列中没有找到，则添加
                    statusToSearch.add(newItem);
                }
            }
            // 可往右搜
            if(curY < N - 1) {
                SearchItem newItem = new SearchItem(curX, curY + 1, RIGHT, curValue + matrix[curX][curY + 1]);
                newItem.lastItem = searchItem;
                // 是否替代
                if(!replaceStatusList(newItem)) {
                    // 搜索队列中没有找到，则添加
                    statusToSearch.add(newItem);
                }
            }
        }
    }
    // 获取最大值
    public int getMaxAward(int[][] matrix) {
        // 初始化
        M = matrix.length;
        N = matrix[0].length;
        int value = matrix[0][0];
        SearchItem searchItem = new SearchItem(0, 0, 0, value);
        statusToSearch.add(searchItem);
        // 开始搜索
        search(matrix);
        // 返回最大值
        return bestItem.value;
    }

    // 打印最佳路径
    public void printBestPath() {
        List<Integer> dirList = new ArrayList<Integer>();
        SearchItem curSearchItem = bestItem;
        // 根据lastItem，找到路径
        while(null != curSearchItem) {
            int dir = curSearchItem.dir;
            if(dir == DOWN) {
                dirList.add(DOWN);
            } else if(dir == RIGHT) {
                dirList.add(RIGHT);
            }
            curSearchItem = curSearchItem.lastItem;
        }
        // 打印路径
        for(int i = dirList.size() - 1; i >= 0; i--) {
            if(dirList.get(i) == DOWN) {
                System.out.print("下 ");
            } else if(dirList.get(i) == RIGHT) {
                System.out.print("右 ");
            }
        }
        System.out.println();
    }
}
