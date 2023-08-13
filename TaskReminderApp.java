import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}


class Task {
    private String title;
    private LocalDate dueDate;
    private boolean completed;
    private Priority priority;

    public Task(String title, LocalDate dueDate, Priority priority) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    public Priority getPriority() {
        return priority;
    }

	public void setTitle(String title) {
    this.title = title;
		}

		public void setDueDate(LocalDate dueDate) {
			this.dueDate = dueDate;
		}

		public void setPriority(Priority priority) {
			this.priority = priority;
		}

		public void setCategory(String category) {
			this.category = category;
		}

	private String category;

    public Task(String title, LocalDate dueDate, Priority priority, String category) {
        this.title = title;
        this.dueDate = dueDate;
        this.priority = priority;
        this.category = category;
        this.completed = false;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        String status = completed ? "Completed" : "Not Completed";
       return "[" + priority + "] " + title + " (Due: " + dueDate + ") - " + status + " [Category: " + category + "]";
    }
}

enum Priority {
    HIGH, MEDIUM, LOW
}

class TaskManager {
       private List<Task> tasks;
    private Map<LocalDate, List<Task>> taskCalendar;

    public TaskManager() {
        tasks = new ArrayList<>();
        taskCalendar = new HashMap<>();
    }

    public void addTask(Task task) {
        tasks.add(task);

        LocalDate dueDate = task.getDueDate();
        taskCalendar.putIfAbsent(dueDate, new ArrayList<>());
        taskCalendar.get(dueDate).add(task);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void markTaskCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markCompleted();
        }
    }

	

    public Map<LocalDate, List<Task>> getTaskCalendar() {
        return taskCalendar;
    }

	 public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task taskToRemove = tasks.get(index);
            LocalDate dueDate = taskToRemove.getDueDate();
            
            tasks.remove(index);
            taskCalendar.get(dueDate).remove(taskToRemove);
            
            System.out.println("Task deleted successfully.");
        }
    }
}


public class TaskReminderApp {
	private static Map<String, User> users = new HashMap<>();
    private static User currentUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskManager taskManager = new TaskManager();
		List<Task> tasks = taskManager.getTasks();

        while (true) {
			System.out.println("--------->Task Reminder<---------");
            System.out.println("     1. User Registration  ");
			System.out.println("     2. Login            ");
			System.out.println("     3. Add Task      ");
			System.out.println("     4. View Tasks   ");
		    System.out.println("     5 .Mark Task Completed  ");
			System.out.println("     6. View Task Calendar");
			System.out.println("     7. Delete Task");
			System.out.println("     8. Edit Task");
			System.out.println("     9. Exit \n");
            System.out.println("Enter your choice:");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
				case 1:
                    
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    users.put(newUsername, new User(newUsername, newPassword));
                    System.out.println("User registered successfully!");
                    break;
				case 2:
                    
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    User user = users.get(username);
                    if (user != null && user.getPassword().equals(password)) {
                        currentUser = user;
                        System.out.println("Login successful!");
                    } else {
                        System.out.println("Login failed. Invalid credentials.");
                    }
                    break;
                case 3:
                    System.out.print("Enter task title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter due date (yyyy-mm-dd): ");
                    LocalDate dueDate = LocalDate.parse(scanner.nextLine());
                    System.out.println("Enter priority (HIGH, MEDIUM, LOW): ");
                    Priority priority = Priority.valueOf(scanner.nextLine().toUpperCase());
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    Task newTask = new Task(title, dueDate, priority, category);
                    taskManager.addTask(newTask);
                    System.out.println("Task added successfully!");
                    break;
                case 4:
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks available.");
                    } else {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println(i + ". " + tasks.get(i));
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter task index to mark as completed: ");
                    int taskIndex = scanner.nextInt();
                    taskManager.markTaskCompleted(taskIndex);
                    System.out.println("Task marked as completed.");
                    break;
				case 6:
					System.out.println("--------->Task Calendar<---------");
					Map<LocalDate, List<Task>> taskCalendar = taskManager.getTaskCalendar();
					if (taskCalendar.isEmpty()) {
						System.out.println("No tasks available.");
					} else {
						for (LocalDate date : taskCalendar.keySet()) {
							System.out.println(date + ":");
							List<Task> tasksForDate = taskCalendar.get(date);
							for (Task task : tasksForDate) {
								System.out.println("  - " + task.getTitle() + " (Priority: " + task.getPriority() + ")");
							}
						}
					}
					break;
				case 7:
					 System.out.print("Enter task index to delete: ");
					int taskIndexToDelete = scanner.nextInt();
					taskManager.deleteTask(taskIndexToDelete);
					break;
				case 8:
					System.out.print("Enter task index to edit: ");
					int taskIndexToEdit = scanner.nextInt();
					scanner.nextLine(); 

					if (taskIndexToEdit >= 0 && taskIndexToEdit < tasks.size()) {
						Task taskToEdit = tasks.get(taskIndexToEdit);

						System.out.print("Enter new task title: ");
						String newTitle = scanner.nextLine();
						System.out.print("Enter new due date (yyyy-mm-dd): ");
						LocalDate newDueDate = LocalDate.parse(scanner.nextLine());
						System.out.println("Enter new priority (HIGH, MEDIUM, LOW): ");
						Priority newPriority = Priority.valueOf(scanner.nextLine().toUpperCase());
						System.out.print("Enter new category: ");
						String newCategory = scanner.nextLine();

						taskToEdit.setTitle(newTitle);
						taskToEdit.setDueDate(newDueDate);
						taskToEdit.setPriority(newPriority);
						taskToEdit.setCategory(newCategory);

						System.out.println("Task edited successfully.");
					} else {
						System.out.println("Invalid task index.");
					}
					break;
				 case 9:
                    System.out.println("Exiting the application.");
                    scanner.close();
                    System.exit(0);
					break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
