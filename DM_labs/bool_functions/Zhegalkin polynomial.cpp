#include <iostream>
#include "math.h"
 
using namespace std;
 
bool isStepOfTwo(int i)
{
    if(i<=2){return true;}
    else
    {
        while(i>2)
        {
            if(i%2!=0)
            {
                return false;
            }
            else
            {
                i/=2;
            }
        }
        return true;
    }
}
 
bool monoton(string& str,int s,int f)
{
    int sumS=0,sumF=0;
    for(int i=s;i<(f+s)/2;i++){
        if(str[i]=='1'){
            sumS+=pow(2,((f+s)/2)-1-i);
        }
    }
    for(int i=(f+s)/2;i<f;i++){
        if(str[i]=='1'){
            sumF+=pow(2,(f-1-i));
        }
    }
    if(sumS>sumF){
        return false;
    } else {
        if(f-s>1){
            if((monoton(str,s,(f+s)/2))&&(monoton(str,(f+s)/2,f))){
                return true;
            }else {return false;}
        }
        else{return true;}
    }
}
 
int main()
{
    int n;
    cin>>n;
    int k = pow(2,n);
    string str[k];
    string mas[k];
    for(int i=0;i<k;i++){cin>>str[i];cin>>mas[i];}
    int i=k-1;string sas[k];
    sas[0]=mas[0];
    while(i>0)
    {
        for(int j=0;j<i;j++)
        {
            if(mas[j]==mas[j+1]){mas[j]='0';}
            else {mas[j]='1';}
            //cout<<mas[j]<<" ";
        }
        //cout<<""<<endl;
        sas[k-i]=mas[0];
        i--;
    }
    for(int i=0;i<k;i++){cout<<str[i]<<" "<<sas[i]<<endl;}
    /*bool mas[5] = {true,true,true,true,true};
    for(int u=0;u<n;u++){
        long k;cin>>k;
        string str;
        if(k>0) {
        cin>>str;
        k=pow(2,k);
        if(str[0]!='0'){mas[0]=false;}
        if(str[k-1]!='1'){mas[1]=false;}
        for(int i=0;i<k;i++){
            if(str[i]==str[k-i-1])
                {mas[2]=false;}
        }
        if(!monoton(str,0,k)){mas[3]=false;}
        //mas[3]=monoton(str,0,k);
        int i=k-1;
        while(i>0){
            for(int j=0;j<i;j++){
                    if(str[j]==str[j+1]){
                    str[j]='0';
                } else {str[j]='1';}
                //cout<<str[j]<<" ";
            }
            if(!isStepOfTwo(k-i))
            {
                //cout<<k-i<<endl;
                if(str[0]=='1')
                {
                    mas[4]=false;
                }
            }
            i--;
        }
        }
        else {
            cin>>str;
            if(str=="0"){
                mas[1]=false;
            }
            else{
                mas[0]=false;
            }
            mas[2]=false;
        }
    }
    string str = "YES";
    for(int u=0;u<5;u++){
        //cout<<mas[u]<<" ";
        if(mas[u]!=false)
            str = "NO";
    }*/
}