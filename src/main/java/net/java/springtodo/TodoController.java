package net.java.springtodo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TodoController {
    private final TodoRepository repo;

    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    // Show all todos
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("todos", repo.findAll());
        return "index";
    }

    // Add new todo
    @PostMapping("/add")
    public String addTodo(@RequestParam String title) {
        repo.save(new Todo(title));
        return "redirect:/";
    }

    // Toggle completion (PUT)
    @PutMapping("/toggle/{id}")
    public String toggleTodo(@PathVariable Long id) {
        Todo todo = repo.findById(id).orElseThrow();
        todo.setCompleted(!todo.isCompleted());
        repo.save(todo);
        return "redirect:/";
    }

    // Delete todo (DELETE)
    @DeleteMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }

    // Update title (PUT)
    @PutMapping("/update/{id}")
    public String updateTodo(@PathVariable Long id, @RequestParam String title) {
        Todo todo = repo.findById(id).orElseThrow();
        todo.setTitle(title);
        repo.save(todo);
        return "redirect:/";
    }
}
