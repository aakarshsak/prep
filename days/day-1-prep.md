# Day 1 Interview Prep — Printable Checklist

_Timebox: 2.5–3 hours total_

---

## Before You Start (5 min)
- [ ] Create a new repo `interview-bootcamp` (private is fine)
- [ ] Make a `day-1/` folder for today’s work
- [ ] Open a notes file `day-1-notes.md` (use the templates below)

---

## 1) DSA — Arrays/Strings Warm‑Up (60–75 min)
**Target problems**
- [ ] Two Sum
- [ ] Best Time to Buy & Sell Stock (I)
- [ ] Contains Duplicate
- [ ] Product of Array Except Self

**Solve workflow (repeat per problem)**
- [ ] 15–20 min: attempt from scratch (brute → optimize)
- [ ] 3–5 min: confirm time/space complexity
- [ ] 5 min: re‑implement from memory (fresh file/tab)

**Quick self‑check**
- [ ] I can state the core idea in 1 sentence
- [ ] I listed at least 2 edge cases
- [ ] I wrote final Big‑O clearly

**Notes template (copy for each problem)**
- **Pattern used:** _______________________________________
- **Key idea (1 line):** __________________________________
- **Edge cases:** _________________________________________
- **Time / Space:** _______________________________________
- **One pitfall I’ll avoid next time:** ___________________

---

## 2) Java — Collections Big Picture (60 min)
**Concepts to (re)cement**
- [ ] List vs Set vs Map: ordering, duplicates, nulls, iteration
- [ ] ArrayList vs LinkedList (random access vs insert/delete)
- [ ] HashSet / LinkedHashSet / TreeSet differences
- [ ] HashMap / LinkedHashMap / TreeMap differences
- [ ] Amortized ops: get/put/contains/remove

**Micro‑drills (code in `day-1/java/`)**
- [ ] `dedupePreserveOrder(List<Integer>)` using `LinkedHashSet`
- [ ] Build frequency map → sort `List<Map.Entry<K,V>>` by value
- [ ] Outline `topKFrequent(int[] a, int k)` with `HashMap` + `PriorityQueue`
- [ ] For each snippet, add a 1‑line comment: _“Using X because Y (ordering/complexity)”_

**Cheat lines (fill from memory)**
- `HashMap` lookup expected Big‑O: _________________________
- Collection to keep insertion order w/o duplicates: ________
- Sorted, log‑time ops Set: ________________________________

---

## 3) Spring Boot Mini‑Build — User CRUD + Validation + Profiles (30–45 min)
**Goal today:** running service with validation & two profiles (dev/prod), **in‑memory store** (DB comes later).

**Generate project (optional quick command)**
```bash
curl https://start.spring.io/starter.zip   -d dependencies=web,validation,lombok   -d name=user-service   -d packageName=com.aakarsh.users   -d javaVersion=17   -o user-service.zip
unzip user-service.zip -d day-1/spring
```

**Model (Java)**
```java
class User { UUID id; String name; String email; String role; }
// Validation: @NotBlank name, @Email email, @Pattern(regexp="ADMIN|USER") role (optional)
```

**Storage**
- [ ] `ConcurrentHashMap<UUID, User>` as repository layer

**Endpoints**
- [ ] POST `/users` → 201 + body, 400 on validation error
- [ ] GET `/users/{id}` → 200 or 404
- [ ] PUT `/users/{id}` → 200 (validate body)
- [ ] DELETE `/users/{id}` → 204

**Profiles**
- [ ] `application-dev.yml` → `server.port: 8081`
- [ ] `application-prod.yml` → `server.port: 8080`

**Run commands**
```bash
# Dev (port 8081)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
# or Gradle
./gradlew bootRun --args='--spring.profiles.active=dev'
```

**Sample payloads**
```json
{
  "name": "Alice",
  "email": "alice@example.com",
  "role": "USER"
}
```

**Quick cURL checks (copy/paste)**
```bash
# Create
curl -i -X POST "http://localhost:8081/users"   -H "Content-Type: application/json"   -d '{"name":"Alice","email":"alice@example.com","role":"USER"}'

# Create (invalid email -> expect 400)
curl -i -X POST "http://localhost:8081/users"   -H "Content-Type: application/json"   -d '{"name":"Bob","email":"not-an-email","role":"ADMIN"}'

# Replace {ID} with the returned UUID
USER_ID={ID}

# Get by id
curl -i "http://localhost:8081/users/$USER_ID"

# Update
curl -i -X PUT "http://localhost:8081/users/$USER_ID"   -H "Content-Type: application/json"   -d '{"name":"Alice Updated","email":"alice@example.com","role":"ADMIN"}'

# Delete
curl -i -X DELETE "http://localhost:8081/users/$USER_ID"

# Unknown id (expect 404)
curl -i "http://localhost:8081/users/00000000-0000-0000-0000-000000000000"
```

**Repo hygiene**
- [ ] README with endpoints + how to run dev/prod
- [ ] Commits: `chore(init)`, `feat(user-crud)`, `feat(validation)`, `chore(profiles)`

---

## End‑of‑Day Deliverables (10–15 min wrap‑up)
- [ ] **DSA:** 3–4 problems with notes (pattern, complexity, pitfalls)
- [ ] **Java:** one‑pager on Collections trade‑offs + 3 code snippets
- [ ] **Spring:** CRUD service running in dev (8081) & prod (8080) profiles
- [ ] Pushed to `interview-bootcamp/day-1/` with clean commits

**Retrospective (fill in)**
- What slowed me down today? ______________________________________
- First thing I’ll try next time when stuck: ________________________
- One edge case I won’t forget: ____________________________________

---

### Scratchpad (use freely)
```
Problem name:
Idea:
Edge cases:
Big-O:
Pitfall:
```

**Done = Moved.** If everything above is checked, move to **Day 2** tomorrow.
