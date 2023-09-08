// Name: Reese Johnson
// Class: CS3305/WO1
// Term: Summer 2023
// Instructor: Prof. Majeed
// Assignment: 4

import java.util.Scanner;


public class ReachabilityMatrix {
    //initialization of a bunch of arrays, so I can use them later in the code for calculations.

    static int[][] adjacencyMatrix;
    static int[][] reachabilityMatrix;
    static int[][] matrixA2;
    static int[][] matrixA3;
    static int[][] matrixA4;
    static int[][] matrixA5;
    private static int numNodes;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        //start of the menu
        while (choice != 3) {
            //try statement to ensure only a number is given
            try {
                System.out.println("------MAIN MENU------");
                System.out.println("1. Enter graph data");
                System.out.println("2. Print outputs");
                System.out.println("3. Exit program");
                System.out.print("Enter option number: ");
                choice = scanner.nextInt();
                switch (choice) {
                    //getting the data
                    case 1:
                        readInput(scanner);
                        break;
                    case 2:
                        //printing out everything required in the assignment
                        if (adjacencyMatrix == null) {
                            System.out.println("Please enter graph data first!");
                        } else {
                            printMatrix("Input", adjacencyMatrix);
                            System.out.println();
                            computeAndPrintReachabilityMatrix();
                            System.out.println();
                            computeAndPrintInDegrees();
                            System.out.println();
                            computeAndPrintOutDegrees();
                            System.out.println();
                            computeAndPrintSelfLoops();
                            computeAndPrintCycles(numNodes);
                            computeAndPrintPathsOne();
                            computeAndPrintPathsNodesAmount(numNodes);
                            computeAndPrintAllPaths();
                            computeAndPrintTotalCycles();

                        }
                        break;
                    case 3:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
                System.out.println();
            } catch (Exception e){
                System.out.println("Please only print a number!");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    //method to multiply matrices since it is needed for getting information later
    static int[][] multiplyMatrix(int numNodes, int first[][], int second[][]) {
        int C[][] = new int[numNodes][numNodes];

        // Multiply the two matrices
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                for (int k = 0; k < numNodes; k++)
                    C[i][j] += first[i][k] * second[k][j];
            }
        }
        return C;
    }
    //printing matrix
    private static void printMatrix(String s, int[][] adjacencyMatrix) {
        // Loop through all rows
        //printing out type of matrix before going through the loop
        System.out.println(s + " Matrix:");
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    //reading the input of the user
    private static void readInput(Scanner scanner) {
        //asks for number of nodes in the graph
        System.out.print("Enter number of nodes (max 5): ");
        numNodes = scanner.nextInt();
        //initializes the array to throw the data in
        adjacencyMatrix = new int[numNodes][numNodes];
        System.out.println("Enter values in the adjacency matrix:");
        //generic for loop to iterate through a 2D array, but this one takes user input for population
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                System.out.print("Enter A1[" + i + "," + j + "]: ");
                adjacencyMatrix[i][j] = scanner.nextInt();
            }
        }
    }
    //Reachability method
    private static void computeAndPrintReachabilityMatrix() {
        //initializing the arrays to be of size[numNodes][numNodes]
        //stops errors from happening if some of the arrays are not used
        reachabilityMatrix = new int[numNodes][numNodes];
        matrixA2 = new int[numNodes][numNodes];
        matrixA3 = new int[numNodes][numNodes];
        matrixA4 = new int[numNodes][numNodes];
        matrixA5 = new int[numNodes][numNodes];

        // multiplying matrices
        for (int h = 0; h < numNodes; h++) {
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    for (int k = 0; k < numNodes; k++) {
                        if (h == 1) {
                            matrixA2 = multiplyMatrix(numNodes, adjacencyMatrix, adjacencyMatrix);
                        }
                        if (h == 2) {
                            matrixA3 = multiplyMatrix(numNodes, matrixA2, adjacencyMatrix);
                        }
                        if (h == 3) {
                            matrixA4 = multiplyMatrix(numNodes, matrixA3, adjacencyMatrix);
                        }
                        if (h == 4) {
                            matrixA5 = multiplyMatrix(numNodes, matrixA4, adjacencyMatrix);
                        }
                    }
                }
            }
            //if no multiplication is needed or done runs this to get "reachability"
            if (numNodes == 1) {
                for (int i = 0; i < adjacencyMatrix.length; i++) {
                    for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                        reachabilityMatrix[i][j] = adjacencyMatrix[i][j];
                    }
                }
            }
        }
        //adds the multiplied matrices together to get reachabilityMatrix
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                reachabilityMatrix[i][j] = adjacencyMatrix[i][j] + matrixA2[i][j] + matrixA3[i][j] + matrixA4[i][j] + matrixA5[i][j];
            }
        }
        //prints
        printMatrix("Reachability", reachabilityMatrix);
    }
    //gets and prints in Degrees
    private static void computeAndPrintInDegrees() {
        System.out.println("In-degrees:");
        //loops through the user input matrix to get the in degrees
        for (int i = 0; i < numNodes; i++) {
            int inDegree = 0;
            //adding up the column
            for (int j = 0; j < numNodes; j++) {
                inDegree += adjacencyMatrix[j][i];
            }
            //prints the degree
            System.out.println("Node " + (i + 1) + " in-degree is " + inDegree);
        }
    }
    //gets and prints the out degree
    private static void computeAndPrintOutDegrees() {
        System.out.println("Out-degrees:");
        //same as in degree but loops and adds the rows instead of columns
        for (int i = 0; i < numNodes; i++) {
            int inDegree = 0;
            for (int j = 0; j < numNodes; j++) {
                inDegree += adjacencyMatrix[i][j];
            }
            System.out.println("Node " + (i + 1) + " out-degree is " + inDegree);
        }
    }
    //computing and printing of the self-loops
    public static void computeAndPrintSelfLoops() {
        int selfLoops = 0;
        //loops and only checks if the i == j and the point in the array is 1
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (i == j) {
                    if (adjacencyMatrix[i][j] == 1) {
                        selfLoops += 1;
                    }
                }
            }
        }
        System.out.println("Total number of self-loops: " + selfLoops);
    }
    //gets the cycles from the matrices that were calculated during multiplication
    public static void computeAndPrintCycles(int nodes) {
        int cycles = 0;
        //a bunch of if statements to check how many nodes are in the system to ensure the correct matrix is used all of them are the same
        if (nodes == 1) {
            //loops through the matrix and only adds the main diagonal of the matrix
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    if (i == j) {
                        cycles += adjacencyMatrix[i][j];
                    }
                }
            }
        } else if (nodes == 2) {
            for (int i = 0; i < matrixA2.length; i++) {
                for (int j = 0; j < matrixA2[i].length; j++) {
                    if (i == j) {
                        cycles += matrixA2[i][j];
                    }
                }
            }
        } else if (nodes == 3) {
            for (int i = 0; i < matrixA3.length; i++) {
                for (int j = 0; j < matrixA3[i].length; j++) {
                    if (i == j) {
                        cycles += matrixA3[i][j];
                    }
                }
            }
        } else if (nodes == 4) {
            for (int i = 0; i < matrixA4.length; i++) {
                for (int j = 0; j < matrixA4[i].length; j++) {
                    if (i == j) {
                        cycles += matrixA4[i][j];
                    }
                }
            }
        } else if (nodes == 5) {
            for (int i = 0; i < matrixA5.length; i++) {
                for (int j = 0; j < matrixA5[i].length; j++) {
                    if (i == j) {
                        cycles += matrixA5[i][j];
                    }
                }
            }
        }
        System.out.println("Total number of cycles of length " + numNodes + " edges: " + cycles);
    }
    //gets the total number of cycles in the system
    public static void computeAndPrintTotalCycles() {
        int cycles = 0;
        //for loop to go through each of the ifs to go through each matrix and add their diagonals to the final counter
        for (int p = 0; p < numNodes; p++) {
            if (p == 0) {
                //same as the other cycle counter adds the main diagonal of the matrix
                for (int i = 0; i < adjacencyMatrix.length; i++) {
                    for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                        if (i == j) {
                            cycles += adjacencyMatrix[i][j];
                        }
                    }
                }
            } else if (p == 1) {
                for (int i = 0; i < matrixA2.length; i++) {
                    for (int j = 0; j < matrixA2[i].length; j++) {
                        if (i == j) {
                            cycles += matrixA2[i][j];
                        }
                    }
                }
            } else if (p == 2) {
                for (int i = 0; i < matrixA3.length; i++) {
                    for (int j = 0; j < matrixA3[i].length; j++) {
                        if (i == j) {
                            cycles += matrixA3[i][j];
                        }
                    }
                }
            } else if (p == 3) {
                for (int i = 0; i < matrixA4.length; i++) {
                    for (int j = 0; j < matrixA4[i].length; j++) {
                        if (i == j) {
                            cycles += matrixA4[i][j];
                        }
                    }
                }
            } else if (p == 4) {
                for (int i = 0; i < matrixA5.length; i++) {
                    for (int j = 0; j < matrixA5[i].length; j++) {
                        if (i == j) {
                            cycles += matrixA5[i][j];
                        }
                    }
                }
            }
        }
        System.out.println("Total number of cycles of length 1 to " + numNodes + " edges: " + cycles);
    }
    //gets the number of paths with only 1 edge
    public static void computeAndPrintPathsOne() {
        int paths = 0;
        //loops through the original input array and counts the number of 1s
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] == 1) {
                    paths += 1;
                }
            }
        }
        System.out.println("Total number of paths of length 1 edge: " + paths);
    }
    //adding and printing the number of paths specifically of the length = to the number of nodes
    public static void computeAndPrintPathsNodesAmount(int nodes) {
        int paths = 0;
        //checks which multiplied array needs to be used since they all vary depending on the number of nodes in the
        //system given the number of nodes it will go to that one then add up the array and print it out
        if (nodes == 1) {
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    paths += adjacencyMatrix[i][j];
                }
            }
        } else if (nodes == 2) {
            for (int i = 0; i < matrixA2.length; i++) {
                for (int j = 0; j < matrixA2[i].length; j++) {
                    paths += matrixA2[i][j];
                }
            }
        } else if (nodes == 3) {
            for (int i = 0; i < matrixA3.length; i++) {
                for (int j = 0; j < matrixA3[i].length; j++) {
                    paths += matrixA3[i][j];
                }
            }
        } else if (nodes == 4) {
            for (int i = 0; i < matrixA4.length; i++) {
                for (int j = 0; j < matrixA4[i].length; j++) {
                    paths += matrixA4[i][j];
                }
            }
        } else if (nodes == 5) {
            for (int i = 0; i < matrixA5.length; i++) {
                for (int j = 0; j < matrixA5[i].length; j++) {
                    paths += matrixA5[i][j];
                }
            }
        }
        System.out.println("Total number of paths of length " + numNodes + " edges: " + paths);
    }
    //adds the reachability matrix since total number of paths is just m1 + m2 + m3 etc..
    //and the reachability matrix is all of those matrices added together.
    public static void computeAndPrintAllPaths() {
        int paths = 0;
        for (int i = 0; i < reachabilityMatrix.length; i++) {
            for (int j = 0; j < reachabilityMatrix[i].length; j++) {
                paths += reachabilityMatrix[i][j];
            }
        }
        System.out.println("Total number of paths of length 1 to " + numNodes + " edges: " + paths);
    }
}