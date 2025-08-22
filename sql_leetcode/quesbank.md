# SQL LeetCode Problems - Question Bank Using MySql

> **📌 Note:** All solutions in this question bank are written for **MySQL** syntax and tested on MySQL environment.

## Problem 175: Combine Two Tables

**🔗 Link:** [LeetCode 175 - Combine Two Tables](https://leetcode.com/problems/combine-two-tables/description/)

### 📋 Problem Description
**Goal:** List every person's FirstName, LastName, with their City, State. If no address exists, show NULL for city/state.

### 🎯 Key Concept
**LEFT JOIN** - Use LEFT JOIN to keep all people in the result set and join address information when available.

### 💡 Approach
- Start with the Person table (left table) to ensure all people are included
- LEFT JOIN with Address table on PersonId 
- If no matching address exists, City and State will automatically show as NULL

### 📝 Solution Template
```sql
-- Solution goes here
SELECT 
    p.FirstName,
    p.LastName,
    a.City,
    a.State
FROM Person p
LEFT JOIN Address a ON p.PersonId = a.PersonId;
```

---

## Problem 176: Second Highest Salary

**🔗 Link:** [LeetCode 176 - Second Highest Salary](https://leetcode.com/problems/second-highest-salary/)

### 📋 Problem Description
**Goal:** Return the second distinct highest salary from Employee(Salary); if it doesn't exist, return NULL.

### 🎯 Key Concept
**DISTINCT salaries & ranking** - Deduplicate salaries (think DISTINCT or DENSE_RANK) so multiple top-equal values don't count as "first & second."

### 💡 Approach
Two classic ideas:
- Find MAX(salary) that is < overall MAX (subquery)
- Use DISTINCT with ORDER BY DESC + LIMIT/OFFSET to skip the first highest
- **Edge case:** only one unique salary → result should be NULL

### 📝 Solution Template
```sql
-- Method 1: Subquery approach
SELECT 
    MAX(Salary) AS SecondHighestSalary
FROM Employee
WHERE Salary < (SELECT MAX(Salary) FROM Employee);

-- Method 2: LIMIT and OFFSET approach
SELECT 
    (SELECT DISTINCT Salary 
     FROM Employee 
     ORDER BY Salary DESC 
     LIMIT 1 OFFSET 1) AS SecondHighestSalary;
```

---

## Problem 177: Nth Highest Salary

**🔗 Link:** [LeetCode 177 - Nth Highest Salary](https://leetcode.com/problems/nth-highest-salary/)

### 📋 Problem Description
**Goal:** Create a function `getNthHighestSalary(N INT)` that returns the Nth distinct highest salary from Employee(Salary); if it doesn't exist, return NULL.

### 🎯 Key Concept
**Function creation & Dynamic N** - Build on Problem 176 concepts but create a reusable function that works for any N value (1st, 2nd, 3rd, etc.).

### 💡 Approach
Two main strategies:
- Use DENSE_RANK() window function to handle duplicates properly and filter by rank = N
- Use DISTINCT with ORDER BY DESC + LIMIT/OFFSET where OFFSET = N-1
- **Edge case:** if N > number of unique salaries → result should be NULL

### 📝 Solution Template
```sql
-- Method 1: DENSE_RANK approach (most robust) - MySQL 8.0+
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
  RETURN (
    SELECT max(Salary)
    FROM (
        SELECT Salary, DENSE_RANK() OVER (ORDER BY Salary DESC) as rn
        FROM Employee
    ) ranked
    WHERE rn = N
  );
END

-- Method 2: LIMIT approach (works in all MySQL versions)
CREATE FUNCTION getNthHighestSalary(N INT) RETURNS INT
BEGIN
    SET N = N-1;
    RETURN (
        SELECT DISTINCT Salary 
        FROM Employee 
        ORDER BY Salary DESC 
        LIMIT N, 1
    );
END
```

---

