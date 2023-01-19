package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

	private static final String DB_USERNAME = "";
	private static final String DB_PASSWORD = "";
	private static final String DB_DATABASE = "";


	public static void main(String[] args) throws Exception{
		Connection connection = DriverManager.getConnection(DB_DATABASE, DB_USERNAME, DB_PASSWORD);

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("1.Показать список всех задач!");
			System.out.println("2.Выполнить задачу");
			System.out.println("3.Создать задачу");
			System.out.println("4.Выйти");

			int comma = scanner.nextInt();

			if(comma == 1) {
				Statement statement = connection.createStatement();
				String SQL_SELECT_TASKS = "select * from task order by id desc;";
				ResultSet result = statement.executeQuery(SQL_SELECT_TASKS);

				while (result.next()) {
					System.out.println(result.getInt("id") + " " + result.getString("name") + " "
							+ result.getString("state"));
				}
			} else if (comma == 2) {
				String sql = "update task set state = 'DONE' where id = ?;";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				System.out.println("Введите id задачи: ");
				int taskId = scanner.nextInt();
				preparedStatement.setInt(1, taskId);
			} else if (comma == 3) {
				String sql = "insert into task(name, state) values(?, 'IN_PROCESS');";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				System.out.println("Введите название задачи: ");
				scanner.nextLine();
				String taskName = scanner.nextLine();
				preparedStatement.setString(1, taskName);
				preparedStatement.executeUpdate();
			} else if (comma == 4 ){
				System.exit(0);
			}
		}

	}
}
