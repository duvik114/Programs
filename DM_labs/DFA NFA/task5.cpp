#include <bits/stdc++.h>

using namespace std;

int n, m, k, l;
bool terminal[101];
bool dTerminal[101];
vector <vector <vector<int>>> go(100000, vector <vector <int>> ((int) 'z' + 1, vector<int> (0)));
int dgo[101][256];
int new_index = 1;
long long can[1001][101];
bool haveTrue(vector <bool> v) {
    for (int i = 0; i <= v.size(); i++) {
        if (v[i])
            return true;
    }
    return false;
}
int main() {
    freopen("problem5.in", "r", stdin);
    freopen("problem5.out", "w", stdout);
    cin >> n >> m >> k >> l;
    for (int i = 0; i < k; i++) {
        int temp;
        cin >> temp;
        terminal[temp] = true;
    }
    for(int i = 0; i < m; i++) {
        int a, b;
        char c;
        cin >> a >> b >> c;
        go[a][c].push_back(b);
    }
    queue<vector <bool>> p; // p - очередь состояний
    map <vector <bool>, int> bitsetToInt; // массив множеств.
    vector <bool> start(101, false);
    start[1] = true;
    bitsetToInt[start] = new_index++;
    p.push(start);
    while (!p.empty()) {
        vector <bool> v  = p.front();
        p.pop();
        for (int i = 'a'; i <= 'z'; i++) {
            vector <bool> qd (101, false);
            for (int j = 0; j <= 100; j++)
                if (v[j])
                    for (int u : go[j][i]) {
                        qd[u] = true;
                    }
            if (haveTrue(qd)) {
                if (bitsetToInt.find(qd) == bitsetToInt.end()) {
                    bitsetToInt[qd] = new_index++;
                    p.push(qd);
                }
                dgo[bitsetToInt[v]][i] = bitsetToInt[qd];
            }
        }
    }
    for (auto now : bitsetToInt) {
        for (int i = 0; i <= 100; i++) {
            if (now.first[i] && terminal[i]) {
                dTerminal[now.second] = true;
            }
        }
    }
    can[0][1] = 1;
    for (int i = 0; i < l; i++) {
        for (int j = 1; j < new_index; j++) {
            if (can[i][j]) {
                for(int v : dgo[j])
                    if (v) {
                        can[i + 1][v] += (can[i][j] % (1000000000 + 7));
                        can[i + 1][v] %= (1000000000 + 7);
                    }
            }
        }
    }
    long long answer = 0;
    for (int i = 1; i < new_index; i++) {
        if (dTerminal[i]) {
            answer += (can[l][i] % (1000000000 + 7));
            answer %= (1000000000 + 7);
        }
    }
    cout << (int) answer;
    return 0;
}