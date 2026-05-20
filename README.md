# TodayOnly

The app only cares about today. Tasks belong to the current day. They expire when the day
expires. Every new day starts with a clean slate.

## Tech Stack
- Kotlin
- Jetpack Compose
- ViewModel + StateFlow
- Hilt (Dependency Injection)
- Room (Database)
- AlarmManager
- Material3

## Screenshots

<p float="left">
  <img src="https://github.com/user-attachments/assets/d532c00f-e57c-434c-96d7-0fd62a704e30" width="150" />
  <img src="https://github.com/user-attachments/assets/fd4414d4-e4a3-408a-a08e-a70e239c32c1" width="150" />
  <img src="https://github.com/user-attachments/assets/19598021-e616-4b46-bcb2-ad95bb61bffd" width="150" />
  <img src="https://github.com/user-attachments/assets/b602058d-830c-4a05-9452-3895937e3f8e" width="150" />
  <img src="https://github.com/user-attachments/assets/cfd8fb5c-483b-4c3e-8e64-c5aa5ada4fbe" width="150" />
  <img src="https://github.com/user-attachments/assets/cde61763-71eb-44b9-88ba-a8a18dc8da0f" width="150" />
  <img src="https://github.com/user-attachments/assets/69c8b91f-3a34-4ccc-a18c-cb8bf19d3ea2" width="150" />
  <img src="https://github.com/user-attachments/assets/0ed1afb7-87df-4334-90ba-e22a14933e46" width="150" /> 
</p>

## Demo

[TodayOnly-Demo.webm](https://github.com/user-attachments/assets/95a730e8-af36-4dc0-bb94-5ff2392f4120)


## Approach

The single biggest decision was: how do you "expire" a task at midnight? The naive approach is a midnight `WorkManager` job that deletes or hides old tasks.

However, every task carries its `createdDay` (days since 1970-01-01 in the
user's local zone). The "today" query is `WHERE createdEpochDay = :today`. The day-reset is
therefore implicit, In essence, the query just stops matching old rows. No midnight job or No
destructive cleanup needed. The expired-todos screen is the same query with `<` instead of `=`.


## Architecture


<img width="484" height="785" alt="Screenshot 2026-05-20 at 3 52 22 PM" src="https://github.com/user-attachments/assets/7c9dbf75-59b6-471e-8cb4-9fa76785507b" />



## What's implemented?

- Add a task for today
- Mark complete / uncomplete
- Tasks persist across launches (Room)
- Tasks from previous days don't appear on the main screen
- Day reset is automatic (no manual cleanup, no midnight job)
- Per-task reminder time (within today only) and Local Notifications via `AlarmManager` 
- "Expired Tasks" screen for previously expired tasks, grouped by day
- Strike-through completed tasks
- Haptic feedback on mark complete
- Input validation and Empty States

## Out of scope
- No accounts, no auth
- No future-day scheduling
- No settings screen
- Deleting/Editing a Task

## Key decisions and tradeoffs

| Decision                                        | Why                                          | Tradeoff                                 |
|-------------------------------------------------|----------------------------------------------|------------------------------------------|
| `createdDay` column, no midnight job            | Testable                                     | But Old rows accumulate forever          |
| `AlarmManager` over `WorkManager` for reminders | Exact firing time matters more               |                                          |
| Hilt over manual DI                             | Easier to test                               | Standard DI library for Android          |
| Room over DataStore                             | Needs querying                               |                                          |
| Single repository, no use-cases                 | App is too small to justify a use-case layer | If it grew, I'd introduce them           |
| Tasks not auto-deleted on expiry                | The "view expired todos" faeture needs them  | storage grows eventually                 |
| Time Handling is abstracted                     | Improve testability                          | Avoids directly calling system time APIs |

## What would I improve with more time?

- Add editTask and DeleteTask features
- Include notification actions (snooze, mark complete)
- Expired Tasks live forever right now. I will add a retention policy like delete tasks older than 30 days (running daily via WorkManager)
- Improve the UX for Notification Permission
- Light/Dark mode support
- Write Unit tests for business logic
- Compose UI tests
- TimeZone change handling if the user flies to a different timezone. Currently, the app trusts 'ZoneId.systemDefault()'
- Home-Screen widget

## What I got stuck on and how I worked through it

### **1. Reminder scheduling**

Another challenge was selecting the appropriate scheduling API for local reminders. I initially considered WorkManager, but after evaluating the tradeoffs around timing precision, I switched to AlarmManager for more accurate reminder delivery.

### **2. `AlarmManager` on Android 12+ requires explicit user permission for `SCHEDULE_EXACT_ALARM`.**

The first version crashed with `SecurityException` on an emulator. I added both
`SCHEDULE_EXACT_ALARM` and `USE_EXACT_ALARM` to the manifest

### **3. Day-reset behaviour - I almost wrote a WorkManager job**

My first instinct was "schedule a worker for midnight that deletes old tasks". However, The query-based design is much efficient

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Run the app on an Android device or emulator

## AI tool usage

AI conversation transcript which has a record of how I used ChatGPT while working on this project.

https://chatgpt.com/share/6a0e0a84-8e5c-83ea-86f1-fd6425e80701
