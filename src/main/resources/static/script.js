// Login functionality
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const userName = document.getElementById('userName').value;
    const password = document.getElementById('password').value;
    const message = document.getElementById('message');

    try {
        const response = await fetch('http://localhost:8073/public/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userName, password })
        });
        const token = await response.text();
        if (response.ok) {
            localStorage.setItem('token', token); // Store token
            window.location.href = 'expense.html';
        } else {
            message.textContent = 'Invalid credentials';
        }
    } catch (error) {
        message.textContent = 'Error: ' + error.message;
    }
});

// Expense save functionality
document.getElementById('expenseForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const description = document.getElementById('description').value;
    const amount = document.getElementById('amount').value;
    const category = document.getElementById('category').value;
    const expenseMessage = document.getElementById('expenseMessage');
    const token = localStorage.getItem('token');

    try {
        const response = await fetch('http://localhost:8073/expense', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': token
            },
            body: JSON.stringify({ description, amount, category })
        });
        if (response.ok) {
            expenseMessage.textContent = 'Expense saved successfully';
            expenseMessage.style.color = 'green';
            document.getElementById('expenseForm').reset();
        } else {
            expenseMessage.textContent = 'Error saving expense';
        }
    } catch (error) {
        expenseMessage.textContent = 'Error: ' + error.message;
    }
});

// Logout functionality
function logout() {
    localStorage.removeItem('token');
    window.location.href = 'index.html';
}