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

## Problem 178: Rank Scores

**🔗 Link:** [LeetCode 178 - Rank Scores](https://leetcode.com/problems/rank-scores/)

### 📋 Problem Description
**Goal:** Rank scores from highest to lowest. Tied scores get same rank, with no gaps in ranking sequence (e.g., 1,1,2,3 not 1,1,3,4).

### 🎯 Key Concept
**DENSE_RANK()** - Window function that assigns consecutive ranks without gaps, perfect for handling ties in ranking scenarios.

### 💡 Approach
- Use DENSE_RANK() OVER (ORDER BY score DESC) to assign ranks
- Tied scores automatically get the same rank
- Next rank after ties is consecutive (no gaps)
- **Requires:** MySQL 8.0+ for window functions

### 📝 Solution Template
```sql
-- DENSE_RANK approach (MySQL 8.0+)
SELECT 
    score, 
    DENSE_RANK() OVER (ORDER BY score DESC) 'rank' 
FROM Scores;
```

---

## Problem 180: Consecutive Numbers

**🔗 Link:** [LeetCode 180 - Consecutive Numbers](https://leetcode.com/problems/consecutive-numbers/)

### 📋 Problem Description
**Goal:** Find all numbers that appear at least three times consecutively in the Logs table. Return distinct numbers that have 3+ consecutive occurrences.

### 🎯 Key Concept
**Self-Join Pattern** - Use multiple self-joins to check consecutive rows and compare their values for pattern matching.

### 💡 Approach
- Self-join Logs table 3 times (l1, l2, l3) to get consecutive rows
- Check that IDs are consecutive: `l1.id = l2.id - 1` AND `l2.id = l3.id - 1`
- Ensure all three numbers are the same: `l1.num = l2.num = l3.num`
- Use DISTINCT to avoid duplicate results

### 📝 Solution Template
```sql
-- Self-join approach (works in all MySQL versions)
SELECT DISTINCT l1.num AS ConsecutiveNums 
FROM Logs l1, Logs l2, Logs l3
WHERE l1.id = l2.id - 1 AND l2.id = l3.id - 1
    AND l1.num = l2.num AND l2.num = l3.num;
```

---

