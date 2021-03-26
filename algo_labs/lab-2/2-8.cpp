#include <stdio.h>
#include <iostream>
#include <list>
#include <iterator>
 
using namespace std;
 
int get_o(int,int[],int[]);
int get(int,int[],int[]);
//void join(int&,int&,int[],int[],int[]);
 
int main()
{
    int n,m;
    scanf("%d %d\n",&n,&m);
    char ch;n++;
    int p[n];
    int op[n];
    int r[n];
    for(int i=0;i<n;i++){p[i]=i;op[i]=0;r[i]=0;}
    for(int i=0;i<m;i++)
    {
        ch=getchar();
        if((ch=='\n')||(ch=='\r')){i--;}
        else if(ch=='j')
        {
            getchar();
            getchar();
            getchar();
            getchar();
            int x,y;
            scanf("%d %d",&x,&y);
            //getchar();
            //join(x,y,p,r,op);
            x=get(x,p,op);
            y=get(y,p,op);
            if(x!=y)
            {
                if(r[x]==r[y]){r[x]++;}
                if(r[x]>r[y]){p[y]=x;op[y]-=op[x];}
                else{p[x]=y;op[x]-=op[y];}
            }
        }
        else if(ch=='a')
        {
            getchar();
            getchar();
            getchar();
            int x,o;
            scanf("%d %d",&x,&o);
            //getchar();
            op[get(x,p,op)]+=o;
        }
        else if(ch=='g')
        {
            getchar();
            getchar();
            getchar();
            int x;
            scanf("%d",&x);
            //getchar();
            printf("%d\n",get_o(x,p,op));
        }
    }
}
 
int get_o(int x,int p[],int op[])
{
    int opit;
    if(p[x]!=x)
    {opit=op[x]+get_o(p[x],p,op);}
    else{opit=op[x];}
    return opit;
}
 
int get(int x,int p[],int op[])
{
    int X=p[x];//just need, juuust neeed
    if(X!=x)
    {
        int opit=get_o(x,p,op);
        p[x]=get(X,p,op);
        op[x]=opit-op[p[x]];
    }
    return p[x];
}
 
/*void join(int& x,int& y,int p[],int r[],int op[])
{
    x=get(x,p,op);
    y=get(y,p,op);
    if(x!=y)
    {
        if(r[x]==r[y]){r[x]++;}
        if(r[x]>r[y]){p[y]=x;op[y]-=op[x];}
        else{p[x]=y;op[x]-=op[y];}
    }
}*/