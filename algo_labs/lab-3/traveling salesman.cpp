#include <stdio.h>
#include <iostream>
#include<bits/stdc++.h>
#include "math.h"
#include <vector>
#include <bitset>
 
using namespace std;
 
int main()
{
    const double INF = 2147483647;
    int n;
    scanf("%d", &n);
    double ways[n][n];
    for(int i = 0;i < n;i++)
    {
        for(int j = 0;j < n;j++)
        {
            //scanf("%d", &ways[i][j]);
            cin>>ways[i][j];
        }
    }
    double dp[(int)pow(2, n - 1) - 1][n];
    int restore_ans[(int)pow(2, n - 1)][n];
 
    dp[0][0] = 0;
    restore_ans[0][0] = -1;
    for(int i = 1;i < n;i++)
        { dp[0][i] = ways[0][i];restore_ans[0][i] = 0; } //мб ещё i = 0 добавить?
 
    for(int mask = 1;mask < pow(2, n - 1) - 1;mask++)//
    {
        dp[mask][0] = INF;
        restore_ans[mask][0] = -1;
        bitset<13> bit_mask(mask);
        for(int i = 1;i < n;i++)
        {
            dp[mask][i] = INF;
            restore_ans[mask][i] = -1;
            if(bit_mask[i - 1] == 1)
                continue;
            double min_way = INF;
            for(int j = 1;j < n;j++)//j == 0 ????
            {
                if(bit_mask[j - 1] != 1)
                    continue;
                if(dp[mask - (int)pow(2, j - 1)][j] < min_way - ways[i][j])
                {
                    min_way = dp[mask - (int)pow(2, j - 1)][j] + ways[i][j];
                    restore_ans[mask][i] = j;
                }
            }
            dp[mask][i] = min_way;
        }
    }
    
    /*for(int i = 0;i < (int)pow(2, n - 1) - 1;i++)
    {
        for(int j = 0;j < n;j++)
        {
            cout<<dp[i][j]<<" ";
        }
        cout<<""<<endl;
    }*/
    
    double res = INF;
    int answers[n];
    int mask = pow(2, n - 1) - 1;
    for(int i = n - 1;i > 0;i--)
    {
        if(dp[mask - (int)pow(2, i - 1)][i] < res)
        {
            res = dp[mask - (int)pow(2, i - 1)][i];
            answers[n - 1] = i;
        }
    }
    res += ways[0][answers[n - 1]];
    int max_start = 0;
    double max_way = ways[0][answers[n - 1]];
    mask = pow(2, n - 1) - 1;
    mask -= (int)pow(2, answers[n - 1] - 1);
    int pos = n - 1;
    while(pos > 0)
    {
        if(ways[restore_ans[mask][answers[pos]]][answers[pos]] > max_way)
        {
            max_way = ways[restore_ans[mask][answers[pos]]][answers[pos]];
            max_start = pos;
        }
        answers[pos - 1] = restore_ans[mask][answers[pos]];
        mask -= (int)pow(2, answers[pos - 1] - 1);
        pos--;
    }
 
    if(n == 1) { cout<<ways[0][0]<<endl<<"1"<<endl; }
    else
    {
        if((int)(res - max_way) == (res - max_way))
            printf("%d\n", int(res - max_way));
        else
            printf("%f\n", (res - max_way));
        pos = max_start - 1;
        if(pos < 0) { pos = n - 1; }
        while(pos != max_start)
        {
            printf("%d ", answers[pos] + 1);
            pos -= 1;
            if(pos < 0)
                pos = n - 1;
        }
        printf("%d", answers[pos] + 1);
    }
}