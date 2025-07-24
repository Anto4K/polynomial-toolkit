# Polynomial Calculator in Java

This project is a **Polynomial Calculator** implemented in Java, featuring an interactive **GUI built with Java Swing**.
The application enables users to perform various algebraic operations on polynomials such as addition, subtraction, multiplication, division, derivation, and integration.

This project was developed during the **Object-Oriented Programming (POO)** course. The graphical interface (GUI) was created as the **final project for the course exam**.

## ✨ Features

- Polynomial parsing and display
- Support for:
  - Addition
  - Subtraction
  - Multiplication
  - Division (with quotient and remainder)
  - Derivation
  - Indefinite Integration
- Interactive GUI with Java Swing components
- Modular and extensible architecture
- Input validation and error handling

## 🛠️ Technologies

- **Java**
- **Java Swing**

## 📁 Project Structure

```
polinomi/
├── InputPolinomio.java       # Handles polynomial input parsing
├── Monomio.java              # Defines monomial structure
├── Polinomio.java            # Interface or base class for polynomials
├── PolinomioAL.java          # ArrayList implementation of polynomial
├── PolinomioAstratto.java    # Abstract class for shared behavior
├── PolinomioGUI.java         # Main GUI class using Swing
├── PolinomioLC.java          # Linked list implementation
├── PolinomioLL.java          # Another linked list-based polynomial
├── PolinomioMap.java         # Map-based polynomial implementation
├── PolinomioSet.java         # Set-based polynomial implementation
```

## 🚀 How to Run

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/polynomial-calculator.git
   cd polynomial-calculator
   ```

2. **Compile the Java files:**
   ```bash
   javac polinomi/*.java
   ```

3. **Run the application:**
   ```bash
   java polinomi.PolinomioGUI
   ```

> Make sure Java is installed and configured in your environment (preferably Java 17 or newer).

## 📚 Educational Context

This project was developed as part of the **Object-Oriented Programming (POO)** course. The GUI and polynomial engine demonstrate the application of principles such as inheritance, abstraction, and modularity, culminating in this graphical calculator as the final assessment.


## 📄 License

This project is distributed under the [MIT License](LICENSE). You are free to use, modify, and distribute it for educational or personal use.

---

**Developed with ❤️ using Java and Swing.**
