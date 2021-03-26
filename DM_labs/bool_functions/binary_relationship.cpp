#include <iostream>
 
using namespace std;
 
void swp(int* mas, int a, int b) {
    int tmp = mas[a];
    mas[a]=mas[b];
    mas[b]=tmp;
}
 
/*void q_sort(int* mas,int s,int f,int random) {
    int e=0,m=0;
    int a[f-s];
    for(int i = 0;i < f-s;i++) {
            a[i]=mas[i+s];
        if(mas[i+s]<random) {
            swp(a,i,m);
            swp(a,m,e);
            e++;m++;
        } else if(mas[i+s]==random){
            swp(a,i,m);
            m++;
        }
    }
    for(int i = 0;i < f-s;i++) {
        mas[i+s]=a[i];
    }
    if(e>1) {
        int r1 = rand()%e;
        q_sort(mas,s,e+s,mas[r1]);
    } if(f-m-s>1) {
        int r2 = rand()%(f-m) + m + s;
        q_sort(mas,m+s,f,mas[r2]);
    }
}*/
 
int main()
{
    int n;
    cin>>n;
    int mas1[n][n];
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cin>>mas1[i][j];
        }
    }
    /*for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cout<<mas1[i][j]<<" ";
        }
        cout<<""<<endl;
    }*/
    int sas1[5] = {0,0,0,0,0};bool flag=true;
 
    for(int i=0;i<n;i++){if(mas1[i][i]!=1){flag=false;}}
    if(flag){sas1[0]=1;}
    else {sas1[0]=0;}flag=true;
 
    for(int i=0;i<n;i++){if(mas1[i][i]!=0){flag=false;}}
    if(flag){sas1[1]=1;}
    else {sas1[1]=0;}flag=true;
 
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            if(mas1[i][j]!=mas1[j][i]){flag=false;}
        }
    }
    if(flag){sas1[2]=1;}
    else {sas1[2]=0;}flag=true;
 
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            if((mas1[i][j]==1)&&(mas1[j][i]==1))
                {if(i!=j)flag=false;}
        }
    }
    if(flag){sas1[3]=1;}
    else {sas1[3]=0;}flag=true;
 
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
if(mas1[i][j]==1){for(int j2=0;j2<n;j2++){if((mas1[j][j2]==1)&&(mas1[i][j2]!=1)){flag=false;}}}
        }
    }
    if(flag){sas1[4]=1;}
    else {sas1[4]=0;}flag=true;
 
    /*
    */
 
    int mas2[n][n];
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cin>>mas2[i][j];
        }
    }
 
    /*
    */
 
    int sas2[5] = {0,0,0,0,0};
 
    for(int i=0;i<n;i++){if(mas2[i][i]!=1){flag=false;}}
    if(flag){sas2[0]=1;}
    else {sas2[0]=0;}flag=true;
 
    for(int i=0;i<n;i++){if(mas2[i][i]!=0){flag=false;}}
    if(flag){sas2[1]=1;}
    else {sas2[1]=0;}flag=true;
 
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            if(mas2[i][j]!=mas2[j][i]){flag=false;}
        }
    }
    if(flag){sas2[2]=1;}
    else {sas2[2]=0;}flag=true;
 
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            if((mas2[i][j]==1)&&(mas2[j][i]==1))
                {if(i!=j)flag=false;}
        }
    }
    if(flag){sas2[3]=1;}
    else {sas2[3]=0;}flag=true;
 
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
if(mas2[i][j]==1){for(int j2=0;j2<n;j2++){if((mas2[j][j2]==1)&&(mas2[i][j2]!=1)){flag=false;}}}
        }
    }
    if(flag){sas2[4]=1;}
    else {sas2[4]=0;}
 
    /*
    */
 
    for(int i=0;i<5;i++){cout<<sas1[i]<<" ";}
    cout<<""<<endl;
    for(int i=0;i<5;i++){cout<<sas2[i]<<" ";}
    cout<<""<<endl;
 
    /*
    */
 
    int mas[n][n];
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            mas[i][j]=0;
        }
    }
 
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
        if(mas1[i][j]==1)
            {
                for(int j2=0;j2<n;j2++)
                    {if(mas2[j][j2]==1)
                        {mas[i][j2]=1;}
                    }
            }
        }
    }
 
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++){
            cout<<mas[i][j]<<" ";
        }
        cout<<""<<endl;
    }
}