package com.todo.service;

import java.util.*;
import java.io.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the title\n");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the description");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		String title = sc.nextLine();
		
		System.out.println("\n"
				+ "========== Delete Item Section\n"
				+ "enter the title of item to remove\n"
				+ "\n");
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Edit Item Section\n"
				+ "enter the title of the item you want to update\n"
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
		
		System.out.println("enter the new description ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("item updated");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("List every items: ");
		for (TodoItem item : l.getList()) {
			System.out.println("Item Title: " + item.getTitle() + "  Item Description:  " + item.getDesc());
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
						String t = st.nextToken();
						String d = st.nextToken();
						String c = st.nextToken();
						TodoItem a = new TodoItem(t,d);
						a.setCurrent_date(c);
						
						l.addItem(a);
						
					}
			
			br.close();
			
			
		}catch(Exception e){
			
			System.out.print("there's no such file.");
			
		}
		
	}
}
