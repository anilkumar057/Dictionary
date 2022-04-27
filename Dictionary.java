import java.util.Comparator;
import java.util.Scanner;

public class Dictionary {
    //Driver Method
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        System.out.println("\nYour welcome in BST Dictionary");
        int choice;
        BstDictionary obj=new BstDictionary();
        do{
            System.out.println("\nEnter you choice");
            System.out.println("1.insert");
            System.out.println("2.update");
            System.out.println("3.delete");
            System.out.println("4.search");
            System.out.println("5.Whole Dictionary in ascending order");
            System.out.println("6.Whole Dictionary in descending order");
            choice=sc.nextInt();
            switch(choice){
                case 1:
                    System.out.print("Enter the word: ");
                    String eleKey=sc.next();
                    System.out.print("Enter the meaning: ");
                    String eleMeaning=sc.next();
                    obj.insert(eleKey, eleMeaning);
                    break;

                case 2:
                    System.out.print("\nEnter the word: ");
                    eleKey=sc.next();
                    System.out.print("Enter the meaning: ");
                    eleMeaning=sc.next();
                    obj.update(eleKey, eleMeaning);
                    break;

                case 3:
                    System.out.print("\nEnter the word: ");
                    eleKey=sc.next();
                    obj.delete(eleKey);
                    break;

                case 4:
                    System.out.print("\nEnter the word: ");
                    eleKey=sc.next();
                    eleMeaning=obj.search(eleKey);
                    System.out.println(eleKey+":   "+eleMeaning);
                    break;

                case 5:
                    obj.printWholeAscending();
                    break;
                
                case 6:
                    obj.printWholeDescending();
                    break;
            }
        }
        while(choice<7);
    }
}

//Node(building block of bst) class 
class Node{
    Node left;
    String key;
    String val;
    Node right;
    Node(String key,String val){
        this.key=key;
        this.val=val;
        this.left=null;
        this.right=null;
    }
}

//mycomparator 
class myComparator implements Comparator<Node>{
    public int compare(Node x,Node y){
        return x.key.compareTo(y.key);
    }
}

//BstDictionary class
class BstDictionary{
    Node root;
    BstDictionary(){
        root=null;
    }

    //insert method
    public void insert(String key,String val){

        //just creating a node to store the give word and it's meaning
        Node buildingBlock=new Node(key,val);

        //if dictionary is empty
        if(root==null){
            root=buildingBlock;
        }
        //if not dictionary is not empty then
        else{
            Node temp=root;
            while(temp!=null){

                //comparing key word with current word
                int comp=key.compareTo(temp.key);
                //if greater
                if(comp>0){
                    //checing it's right subtree and if empty then just add
                    if(temp.right==null){
                        temp.right=buildingBlock;
                        break;
                    }
                    //else go in right subtree
                    else temp=temp.right;
                }
                //if less
                else if(comp<0){
                    if(temp.left==null){
                        temp.left=buildingBlock;
                        break;
                    }
                    else temp=temp.left;
                }
                //if key word is present in dictionary then break
                else break;
            }
        }
    }

    //updating 
    public void update(String key,String val){
        Node temp=root;

        //finding word present in dictionary or not
        while(temp!=null){
            int comp=key.compareTo(temp.key);
            if(comp>0) temp=temp.right;
            else if(comp<0) temp=temp.left;
            //if present the updating
            else{
                temp.val=val;
                break;
            }
        }
    }

    //delete method
    public void delete(String key){
        Node parent=null;
        Node temp=root;

        //finding the word present in dictionary or not
        while(temp!=null){

            //comparing the key word with current word of tree
            int comp=key.compareTo(temp.key);

            //if key word is greater then move to right subtree
            if(comp>0) {
                parent=temp;
                temp=temp.right;
            }

            //if key word is less then move to left subtree
            else if(comp<0) {
                parent=temp;
                temp=temp.left;
            }

            //if equal then just break and go to delete
            else break;
        }

        //if present the going a head to deletion
        if(temp!=null){

            //if key word is leaf word then just delete
            if(temp.left==null && temp.right==null){
                if(temp==root) root=null;
                else if(parent.left==temp) parent.left=null;
                else parent.right=null;
            }

            else if(temp.left==null){
                //if key word is root of tree
                if(temp==root) root=temp.right;
                else if(parent.left==temp) parent.left=temp.right;
                else parent.right=temp.right;
            }

            else if(temp.right==null){
                if(temp==root) root=temp.left;
                //if key word have right subtree is null
                else if(parent.left==temp) parent.left=temp.left;
                else parent.right=temp.right;
            }
            else{
                //finding successor node of key word
                Node succ=sucessor(temp.right);
                //storing value of sucessor word
                String value=succ.val;
                //storing key of sucessor word
                String keys=succ.key;
                //deleting sucessor node
                delete(keys);
                //puting value of sucessor word value at the place of key word
                temp.key=keys;
                temp.val=value;
            }
        }
    }

    //sucessor of any node
    Node sucessor(Node temp){
        while(temp.left!=null) temp=temp.left;
        return temp;
    }

    //search method
    public String search(String key){
        String ans="";
        Node temp=root;

        //loop to find the word present in dictionary or not
        while(temp!=null){
            int comp=key.compareTo(temp.key);
            if(comp>0) temp=temp.right;
            else if(comp<0) temp=temp.left;
            else {
                ans=temp.val;
                break;
            }
        }

        //if not present then just a message
        if(temp==null){
            System.out.println("your search not found");
        }
        return ans;
    }

    //printing whole dictionary in ascending order
    public void printWholeAscending(){
        System.out.println("\nWhole Dictionary in ascending order");

        //recursive method to print all word in ascending order
        printAscending(root);
    }

    //recursive method
    void printAscending(Node temp){
        if(temp==null) return;

        //recursive call for left subtree
        printAscending(temp.left);

        //printing current word and meaning
        System.out.println(temp.key+":   "+temp.val);

        //recursive call to right subtree
        printAscending(temp.right);
    }

    //printing whole dictionary in decending order
    public void printWholeDescending(){
        System.out.println("\nWhole Dictionary in descending order");
        printDescending(root);
    }

    //recursive method to print whole dictionay in descending order
    void printDescending(Node temp){
        if(temp==null) return;
        printDescending(temp.right);
        System.out.println(temp.key+":    "+temp.val);
        printDescending(temp.left);
    }
}

