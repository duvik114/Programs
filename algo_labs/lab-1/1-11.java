import java.util.Scanner;
 
public class Main {
 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
		int n = sc.nextInt(), k = sc.nextInt();
		int[] mas = new int[n];
		for(int i = 0; i < n; i++) {
		    mas[i] = sc.nextInt();
		}
		long min = -1, l = 0, m = Long.MAX_VALUE;
		while(m-l>1) {
		    min = (m + l)/2;
		    long sum = 0, schetchik = 0;
		    boolean flag = false;
		    for(int i = 0; i < n; i++) {
		        if(mas[i] > min) {
		            l = min;
		            flag = true;
		            break;
		        } else if(sum + mas[i] > min) {
		            sum = mas[i];
		            schetchik++;
		            if(i + 1 == n) {schetchik++;}
		            if(mas[i] > min) {
		                l = min;
		                flag = true;
		                break;
		            }
		        } else {
		            sum += mas[i];
		            if(i + 1 == n) {schetchik++;}
		        }
		    }
		    if(!flag) {
		        if(schetchik > k) {
		            l = min;
		        } else {
		            m = min;
		            //System.out.println(m);
		        }
		    }
		}
		long sum = 0, schetchik = 0;
		boolean flag = false;
		for(int i = 0; i < n; i++) {
		    if(mas[i] > l) {
		            flag = true;
		            break;
		        } else if(sum + mas[i] > l) {
		            sum = mas[i];
		            schetchik++;
		            if(i + 1 == n) {schetchik++;}
		        } else {
		            sum += mas[i];
		            if(i + 1 == n) {schetchik++;}
		        }
		   }
		if(!flag) {
		        if(schetchik == k) {
		            System.out.print(l);
		        } else {
		            System.out.print(m);
		        }
		} else {
		    System.out.print(m);
		}
    }
}