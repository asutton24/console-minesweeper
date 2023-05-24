import java.util.Scanner;
public class main {
    static int x;
    static int y;
    static int xMine;
    static int yMine;
    static int game = 0;
    static int[][] board;

    public static void display() {
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                if (board[j][i] > 9 && board[j][i] < 20) {
                    System.out.print("X ");
                } else if (board[j][i] >= 1 && board[j][i] <= 8) {
                    System.out.print(board[j][i] + " ");
                } else if (board[j][i] == 0||board[j][i]==42) {
                    System.out.print("O ");
                } else if (board[j][i]>=20 && board[j][i]<=29){
                    System.out.print("F ");
                } else if (board[j][i]==9){
                    System.out.print("M ");
                }
            }
            System.out.println();
        }
    }

    public static void randomMine() {
        xMine = (int) (Math.random() * x);
        yMine = (int) (Math.random() * y);
    }

    public static int stringToInt(String dig) {
        int returnX = 0;
        if (dig.equals("0")) {
            returnX = 0;
        } else if (dig.equals("1")) {
            returnX = 1;
        } else if (dig.equals("2")) {
            returnX = 2;
        } else if (dig.equals("3")) {
            returnX = 3;
        } else if (dig.equals("4")) {
            returnX = 4;
        } else if (dig.equals("5")) {
            returnX = 5;
        } else if (dig.equals("6")) {
            returnX = 6;
        } else if (dig.equals("7")) {
            returnX = 7;
        } else if (dig.equals("8")) {
            returnX = 8;
        } else if (dig.equals("9")) {
            returnX = 9;
        }
        return returnX;
    }

    public static int xCord(String xc) {
        String xc2 = xc.substring(0, xc.indexOf(","));
        int xlength = xc2.length();
        int xFinal = 0;
        for (int i = 0; i < xc2.length(); i++) {
            String xdig = xc2.substring(i, i + 1);
            int xAns = stringToInt(xdig);
            xAns *= Math.pow(10,(double)(xc2.length() - 1 - i));
            xFinal += xAns;
        }
        xFinal--;
        return xFinal;
    }

    public static int yCord(String yc) {
        String yc2 = yc.substring(yc.indexOf(",") + 1, yc.length());
        int yFinal = 0;
        for (int i = 0; i < yc2.length(); i++) {
            String ydig = yc2.substring(i, i + 1);
            int yAns = stringToInt(ydig);
            yAns *= Math.pow(10,(double)(yc2.length() - 1 - i));
            yFinal += yAns;
        }
        yFinal--;
        return yFinal;
    }

    public static void open(int openX, int openY, int num){
        board[openX][openY]=num;
        if (openX!=0 && openY!=0){
            board[openX-1][openY-1]=num;
        }
        if (openY!=0){
            board[openX][openY-1]=num;
        }
        if (openX!=x-1 && openY!=0){
            board[openX+1][openY-1]=num;
        }
        if (openX!=x-1){
            board[openX+1][openY]=num;
        }
        if (openX!=x-1 && openY!=y-1){
            board[openX+1][openY+1]=num;
        }
        if (openY!=y-1){
            board[openX][openY+1]=num;
        }
        if (openX!=0 && openY!=y-1){
            board[openX-1][openY+1]=num;
        }
        if (openX!=0){
            board[openX-1][openY]=num;
        }
    }

    public static void fixBoard(){
        boolean remove;
        for (int i=0; i<y;i++){
            for (int j=0; j<x;j++){
                remove = false;
                if (board[j][i]<19 && board[j][i]>9){
                    if (i!=0 && board[j][i-1]==0){
                        remove = true;
                    }
                    else if (i!=y-1 && board[j][i+1]==0){
                        remove = true;
                    }
                    else if (j!=0 && board[j-1][i]==0){
                        remove = true;
                    }
                    else if (j!=x-1 && board[j+1][i]==0){
                        remove = true;
                    }
                    else if (i!=0 && j!=0 && board[j-1][i-1]==0){
                        remove = true;
                    }
                    else if (i!=0 && j!=x-1 && board[j+1][i-1]==0){
                        remove = true;
                    }
                    else if (i!=y-1 && j!=0 && board[j-1][i+1]==0){
                        remove = true;
                    }
                    else if (i!=y-1 && j!=x-1 && board[j+1][i+1]==0){
                        remove = true;
                    }
                    if (remove) {
                        board[j][i]-=10;
                    }
                    remove = false;
                }
            }
        }
    }

    public static int zeroCheck(){
        int zcheck = 0;
        for (int i=0;i<y;i++){
            for (int j=0;j<x;j++){
                if (board[j][i]==0){
                    zcheck++;
                }
            }
        }
        return zcheck;
    }

    public static boolean complete(){
        for (int i=0;i<y;i++){
            for (int j=0;j<x;j++){
                if (board[j][i]==19){
                    return false;
                }
            }
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the width of the board:");
        x = scan.nextInt();
        System.out.println("Enter the height of the board:");
        y = scan.nextInt();
        System.out.println("How many mines?");
        int mines = scan.nextInt();
        int mineTemp = 0;
        int zero1 = 0;
        int zero2 = 0;
        int remainingF = mines;
        board = new int[x][y];
        int tick = 0;
        for (int i = 0; i < (y); i++) {
            for (int j = 0; j < x; j++) {
                board[j][i] = 10;
            }
        }
        System.out.println("Enter move in the form x,y with no spaces, add a space and the letter f and the end to add a flag");
        display();
        boolean fPresent;
        String move = scan.nextLine();
        move = scan.nextLine();
        if (move.indexOf("f")!=-1){
            move = move.substring(0,move.length()-2);
            fPresent = true;
        } else {
            fPresent = false;
        }
        int xVal = xCord(move);
        int yVal = y-1-yCord(move);
        open(xVal,yVal,42);
        for (int i=0;i<mines;i+=0){
            randomMine();
            if (board[xMine][yMine]!=42 && board[xMine][yMine]!=19){
                board[xMine][yMine]+=9;
                i++;
            }
        }
        open(xVal,yVal,10);
        for (int i=0;i<y;i++){
            for (int j=0;j<x;j++){
               if (board[j][i]!=19){
                   if (i!=0 && j!=0 && board[j-1][i-1]==19){
                       mineTemp++;
                   }
                   if (i!=0 && board[j][i-1]==19){
                       mineTemp++;
                   }
                   if (i!=0 && j!=(x-1) && board[j+1][i-1]==19){
                       mineTemp++;
                   }
                   if (j!=(x-1) && board[j+1][i]==19){
                       mineTemp++;
                   }
                   if (i!=(y-1) && j!=(x-1) && board[j+1][i+1]==19){
                       mineTemp++;
                   }
                   if (i!=(y-1) && board[j][i+1]==19){
                       mineTemp++;
                   }
                   if (i!=(y-1) && j!=0 && board[j-1][i+1]==19){
                       mineTemp++;
                   }
                   if (j!=0 && board[j-1][i]==19){
                       mineTemp++;
                   }
                   board[j][i]+=mineTemp;
                   mineTemp=0;
               }
            }
        }
        boolean first = true;
        while (game==0){
            if(!first){
                move = scan.nextLine();
                if (move.indexOf("f")!=-1){
                    move = move.substring(0,move.length()-2);
                    fPresent = true;
                } else {
                    fPresent = false;
                }
                xVal = xCord(move);
                yVal = y-1-yCord(move);
            } else {
                first = false;
            }
            if (fPresent){
                if (board[xVal][yVal]>19){
                    board[xVal][yVal]-=10;
                    remainingF++;
                } else {
                    if (remainingF>0){
                        board[xVal][yVal]+=10;
                        remainingF--;
                    }
                }
            } else {
                if (board[xVal][yVal]==19){
                    board[xVal][yVal]-=10;
                    game=1;
                } else if (board[xVal][yVal]>9 && board[xVal][yVal]<19){
                    board[xVal][yVal]-=10;
                }
            }
            zero1= 0;
            zero2 = 1;
            while (zero1!=zero2){
                zero1 = zeroCheck();
                fixBoard();
                zero2 = zeroCheck();
            }
            display();
            if (remainingF == 0){
               if (complete()) {
                   game=2;
               }
            }
        }
        if (game==1){
            for (int i=0;i<y;i++){
                for (int j=0;j<x;j++){
                    if (board[j][i]==19){
                        board[j][i]=9;
                    }
                }
            }
            System.out.println("You Lose!");
        } else {
                System.out.println("You Win!");
            }
    }
}

