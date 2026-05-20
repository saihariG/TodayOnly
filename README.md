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
  <img src="" width="150" />
  <img src="" width="150" /> 
  <img src="" width="150" />
  <img src="" width="150" />
</p>

## Approach



## Architecture



## What's implemented?

- Add a task for today
- Mark complete / uncomplete
- Tasks persist across launches (Room)
- Tasks from previous days don't appear on the main screen
- Day reset is automatic (no manual cleanup, no midnight job)
- Per-task reminder time (within today only) and Local Notifications via `AlarmManager` 
- "Expired Tasks" screen for previously expired tasks, grouped by day
- Input validation

## Out of scope
- No accounts, no auth
- No future-day scheduling
- No settings screen
- Light/Dark mode support
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
- Improve better UX for Notification Permission


## What I got stuck on and how I worked through it

### Reminder scheduling

Another challenge was selecting the appropriate scheduling API for local reminders. I initially considered WorkManager, but after evaluating the tradeoffs around timing precision, I switched to AlarmManager for more accurate reminder delivery.

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Run the app on an Android device or emulator

## AI tool usage

https://chatgpt.com/share/6a0e0a84-8e5c-83ea-86f1-fd6425e80701 














