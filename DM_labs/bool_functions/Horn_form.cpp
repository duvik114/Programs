#include <iostream>
#include "math.h"
 
using namespace std;
 
/*djfsjdf2*/
 
void method(long k,long n,long mas[])
{
    for(long i=n-1;i>=0;i--)
    {
        if(pow(2,i)<=k)
        {
            mas[n-i-1]=1;
            k-=pow(2,i);
        }
        else
        {
            mas[n-i-1]=0;
        }
    }
}
 
int main()
{
    int n,k;
    cin>>n>>k;
    int sas[n];
    int mas[k][n];
    long ras=pow(2,n);
    long coin=ras;
    long bas[ras];
    for(int i=0;i<ras;i++){bas[i]=1;}
    for(int i=0;i<k;i++)
    {
        long sch=0;
        for(int j=0;j<n;j++)
        {
            cin>>mas[i][j];
            if(mas[i][j]==-1){sch++;}
        }
        long ass=pow(2,sch);
        long sass[sch];
        if(sch==0)
        {
            long res=0;
            for(int j=0;j<n;j++)
            {if(mas[i][j]==0){res+=pow(2,n-j-1);}}
            if(bas[res]==1){coin--;}
            bas[res]=0;
        }
        else
        {
        for(int j=0;j<ass;j++)
        {
            long res=0;
            method(j,sch,sass);
            //for(long u=0;u<sch;u++){cout<<sass[u]<<" ";}
            //cout<<" "<<endl;
            long ch=0;
            for(int h=0;h<n;h++)
            {
                if(mas[i][h]==0){res+=pow(2,n-h-1);}
                else if(mas[i][h]==-1)
                {
                    //cout<<sass[ch]<<" "<<ch<<" "<<endl;
                    if(sass[ch]==0){res+=pow(2,n-h-1);}
                    ch++;
                }
            }
            if(bas[res]==1){coin--;}
            bas[res]=0;
        }
        }
    }
 
    /*for(int i=0;i<k;i++)
    {
        bool flag=true;
        for(int j=0;j<n;j++){if(mas[i][j]!=-1){flag=false;}}
        if(!flag)
        {*/
        /*for(int j=0;j<ras;j++)
        {
            if(bas[j]!=0)
            {
            bool res=false;
            method(j,n,sas);
            for(int h=0;h<n;h++)
            {if(mas[i][h]==sas[h]){res=true;}}
            if(!res){bas[j]=0;}
            }
        }*/
        //}
    //}
    /*string str="YES";
    for(int i=0;i<ras;i++)
        {
            //cout<<bas[i]<<" ";
            if(bas[i]!=0){str="NO";}
        }
    cout<<str<<endl;*/
    if(coin>0){cout<<"NO";}
    else{cout<<"YES";}
}