#include <bits/stdc++.h>

using namespace std;

int n, m, k;
vector <int> ans;
long long paths[100001];
bool visit[100001];
bool used[100001];
int color[100001];
bool terminal[100001];
int go[100001][256];
vector <int> black;
bool dfs (int v) {
    color[v] = 1;
    for (int to : go[v]) {
        if (color[to] == 0 && to) {
            if (dfs(to)) return true;
        }
        else if (color[to] == 1) {
            black.push_back(to);
            return true;
        }
    }
    color[v] = 2;
    return false;
}

void dfsForSort (int v) {
    used[v] = true;
    for (int to : go[v]) {;
        if (!used[to])
            dfsForSort (to);
    }
    ans.push_back(v);
}

bool push_black(int v) {
    if (terminal[v]) {
        return true;
    }
    if (visit[v])
        return false;
    else
        visit[v] = true;
    for (int to : go[v]) {
        if (to)
            if (push_black(to)) return true;
    }
    return false;
}

long long amount_ways () {
    dfsForSort(1);
    reverse(ans.begin(), ans.end());
    long long answer = 0;
    paths[1] = 1;
    for (int i : ans) {
        for (int c : go[i]) {
            if(c) {
                paths[c] += paths[i];
                paths[c] %= (1000000000 + 7);
            }
        }
        if (terminal[i]) {
            answer += paths[i];
            answer %= (1000000000 + 7);
        }
    }
    return answer;
}
int main() {
    freopen("problem3.in", "r", stdin);
    freopen("problem3.out", "w", stdout);
    cin >> n >> m >> k;
    for (int i = 0; i < k; i++) {
        int temp;
        cin >> temp;
        terminal[temp] = true;
    }
    for(int i = 0; i < m; i++) {
        int a, b;
        char c;
        cin >> a >> b >> c;
        go[a][c] = b;
    }

    if  (dfs(1)) {
        for (int i : black) {
            if (push_black(i)) {
                cout << -1;
                return 0;
            }
        }
    }
    cout << amount_ways() % (1000000000 + 7);
    return 0;
}