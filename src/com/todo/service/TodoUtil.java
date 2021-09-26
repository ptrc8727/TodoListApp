package com.todo.service;

import java.util.*;
import java.io.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String cate, title, desc, duedate;
		Scanner sc = new Scanner(System.in);
		
		
		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the category\n");
		
		cate = sc.nextLine();
		
		System.out.println("\n"
				+ "enter the title\n");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the description");
		desc = sc.nextLine();
		
		System.out.println("enter the duedate(yyyy-MM-dd HH:mm:ss)");
		duedate = sc.nextLine();
		
		TodoItem t = new TodoItem(cate, title, desc, duedate);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String snum = sc.nextLine();
		int a = Integer.parseInt(snum);
		int b = l.len();
		System.out.println("\n"
				+ "========== Delete Item Section\n"
				+ "enter the serial number of item to remove\n"
				+ "\n");
		
		if (0<a&&a<=b) {
			
					TodoItem ii= l.get(a-1);
					l.deleteItem(ii);

		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Edit Item Section\n"
				+ "enter the category of the item you want to update\n"
				+ "\n");
		
		System.out.println("enter the title of the item you want to update\n"
				+ "\n");
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("title doesn't exist");
			return;
		}

		System.out.println("enter the new title of the item");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the new category ");
		String new_cate = sc.nextLine().trim();
		
		System.out.println("enter the new description ");
		String new_description = sc.nextLine().trim();
		
		System.out.println("enter the new duedate(yyyy-MM-dd HH:mm:ss) ");
		String new_duedate = sc.nextLine().trim();
		
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_cate, new_title, new_description,new_duedate);
				l.addItem(t);
				System.out.println("item updated");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("List every items: ");
		int a = l.len();
		System.out.println("total ["+a+"] elements");
		int i=1;
		for (TodoItem item : l.getList()) {
			
			System.out.println(i+" Category"+item.getCategory()+"Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc()+" Duedate:  "+item.getDuedate()+"Inserted date: "+item.getCurrent_date());
			i = i+1;
		}
	}
	
	public static void find(TodoList l, String str) {
		
		ArrayList<TodoItem> lis = new ArrayList<TodoItem>();
		for (TodoItem item : l.getList()) {
			
			if(item.toSaveString().indexOf(str)>-1) {
				
				lis.add(item);
				
			}
			
		}
		
		int num = lis.size();
		if(num==0) {
			
			System.out.println("There are no such keyword.");
			
		}else {
			
			System.out.println("There are "+num+" number of elements.");
			
			for(TodoItem item : lis) {
				
				int i = l.indexOf(item)+1;
				System.out.println(i+" Category"+item.getCategory()+"Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc()+" Duedate:  "+item.getDuedate()+"Inserted date: "+item.getCurrent_date());
				
			}
			
		}
		
	}
	
	public static void saveList(TodoList l,String filename) {
		
		System.out.println("save the List as a file named \'todolist.txt\'");
		
		try (FileWriter f = new FileWriter(filename)) {
			
			for(TodoItem item : l.getList()) {
				
			f.write(item.toSaveString());
			
			}
			
			f.close();
			
		} catch (Exception e) {			
			System.out.println("exception");
		}
		
	}
	
	public static void loadList(TodoList l, String filename) {
		
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
			
					String str;
					
					System.out.println("load the List from \'todolist.txt\'");
					
					while ((str = br.readLine()) != null) {
					
						StringTokenizer st = new StringTokenizer(str,"##",false);
						String ct = st.nextToken();
						String t = st.nextToken();
						String d = st.nextToken();
						String c = st.nextToken();
						String dd = st.nextToken();
						TodoItem a = new TodoItem(ct,t,d,dd);
						a.setCurrent_date(c);
						
						l.addItem(a);
						
					}
			
			br.close();
			
			
		}catch(Exception e){
			
			System.out.print("there's no such file.");
			
		}
		
	}
}
