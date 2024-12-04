import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

class Student {
    private int id;
    private String name;
    private String course;

    public Student(int id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}

public class StudentManagementGUI {
    private ArrayList<Student> students = new ArrayList<>();
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    public StudentManagementGUI() {
        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Student Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(70, 130, 180));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Table Setup
        String[] columnNames = {"ID", "Name", "Course"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Serif", Font.PLAIN, 16));
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setSelectionForeground(Color.BLACK);

        // Center alignment for table content
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton addButton = createStyledButton("Add", new Color(50, 205, 50));
        JButton updateButton = createStyledButton("Update", new Color(255, 215, 0));
        JButton deleteButton = createStyledButton("Delete", new Color(220, 20, 60));
        JButton exitButton = createStyledButton("Exit", new Color(70, 130, 180));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void addStudent() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField courseField = new JTextField();
        Object[] message = {
            "ID:", idField,
            "Name:", nameField,
            "Course:", courseField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Add Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String idText = idField.getText().trim();
                String name = nameField.getText().trim();
                String course = courseField.getText().trim();

                if (idText.isEmpty() || name.isEmpty() || course.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(idText);

                for (Student student : students) {
                    if (student.getId() == id) {
                        JOptionPane.showMessageDialog(frame, "Error: Student ID must be unique.", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                students.add(new Student(id, name, course));
                tableModel.addRow(new Object[]{id, name, course});
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. ID must be a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a student to update.");
            return;
        }

        JTextField nameField = new JTextField(tableModel.getValueAt(selectedRow, 1).toString());
        JTextField courseField = new JTextField(tableModel.getValueAt(selectedRow, 2).toString());
        Object[] message = {
            "Name:", nameField,
            "Course:", courseField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Update Student", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String course = courseField.getText().trim();

            if (name.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.setValueAt(name, selectedRow, 1);
            tableModel.setValueAt(course, selectedRow, 2);

            Student student = students.get(selectedRow);
            student.setName(name);
            student.setCourse(course);
        }
    }

    private void deleteStudent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a student to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            students.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}
