#include <bits/stdc++.h>

using namespace std;

bool terminal[100001];
int go[100001][256];

int main() {
    freopen("problem1.in", "r", stdin);
    freopen("problem1.out", "w", stdout);
    string s;
    cin >> s;
    int n, m, k;
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
        go[a][(int) c] = b;
    }
    int cur = 1;
    for(char i : s) {
        if (go[cur][(int) i] == 0) {
            cout << "Rejects";
            return 0;
        }
        cur = go[cur][(int) i];
    }
    if (terminal[cur])
        cout << "Accepts";
    else
        cout << "Rejects";

    return 0;
}