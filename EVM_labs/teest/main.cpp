#include<iostream>
#include<vector>
using namespace std;
/*vector<int>Merge(vector<int>a,vector<int>b)
{
    int i=0,j=0;
    vector<int>vec;
    while(i+j<a.size()+b.size())
    {
        if((j==b.size())||(a[i]<b[j]))
        {
            vec.push_back(a[i]);
            i++;
        }
        else
        {
            vec.push_back(b[j]);
            j++;
        }
    }
    return vec;
}
vector<int>Merge_Sort(vector<int>vec)
{
    if(vec.size()==1){return vec;}
    else
    {

        //vector<int>a.copy_n(vec.begin(),(vec.size()-1)/2,)
        vector<int>a;vector<int>b;
        for(int i=0;i<(vec.size()-1)/2;i++) {a.push_back(vec[i]);}
        for(int i=(((vec.size()-1)/2)+1);i<vec.size();i++) {b.push_back(vec[i]);}
        for(int i=0;i<a.size();i++) {cout<<a[i]<<" ";}
        cout<<"        -        ";
        for(int i=0;i<b.size();i++) {cout<<b[i]<<" ";}
        cout<<" "<<endl;
        Merge_Sort(a);Merge_Sort(b);return Merge(a,b);
    }
}*/
int main()
{
    setlocale(LC_ALL,"Russian");
    /*/int n,m;
    cin>>n;
    vector <int> vec;
    for(int i=0;i<n;i++) {cin>>m;vec.push_back(m);}
    Merge_Sort(vec);
    for(int i=0;i<n;i++) {cout<<vec[i]<<" ";}*/
    int n,m;
    cout<<"������� ����� ������� ����� �������������� � zig-zag : ";cin>>n;
    cout<<"������� ����� ������� ����� ���������������� �� zig-zag : ";cin>>m;
    if(n<0){n=abs(n)*2-1;}
    else if(n>0){n*=2;}
    cout<<n<<" : ";
    if(m!=0)
    {
        if(m%2==0){m/=2;}
        else{m=-(m+1)/2;}
    }
    cout<<m;
}
