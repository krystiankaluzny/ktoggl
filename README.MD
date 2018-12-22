## KToggl

KToggl is Kotlin client for the [Toggl API v8](https://github.com/toggl/toggl_api_docs/blob/master/toggl_api.md) 
and the [Toggl Reports API v2](https://github.com/toggl/toggl_api_docs/blob/master/reports.md)

Project is under heavy development and its API (packages, classes, methods names) may change without warning.


### Skipped endpoints

Some Toggl API endpoints was skipped in current implementation because of its low importance (based on my opinion).
Eventually all the endpoints should find itself in KToggl.

If you think that something should be added faster, feel free to create an issue.

#### API implementation status:

1. Authentication
    1. HTTP Basic Auth with e-mail and password - NO
    2. HTTP Basic Auth with API token - YES
    3. Authentication with a session cookie - NO
    4. Destroy the session - NO

2. Clients
    1. Create a client - NO
    2. Get client details - NO
    3. Update a client - NO
    4. Delete a client - NO
    6. Get clients visible to user - NO
    7. Get client projects - NO

3. CORS whitelists
    1. Create an entry - NO
    2. Get entries for current user - NO
    3. Delete an entry - NO

4. Dashboard
    1. Get Dashboard data - NO
 
5. Groups
    1. Create a group - NO
    2. Update a group - NO
    3. Delete a group - NO

6. Project users
    1. Create a project user - NO
    2. Update a project user - NO
    3. Delete a project user - NO
    4. Get list of project users in a Workspace - NO
    5. Create multiple project users for single project - NO
    6. Mass update for project users - NO
    7. Delete multiple project users - NO
   
7. Projects
    1. Create project - NO
    2. Get project data - NO
    3. Update project data - NO
    4. Delete a project - NO
    5. Get project users - NO
    6. Get project tasks - NO
    7. Delete multiple projects - NO

8. Tags
    1. Create tag - NO
    2. Update a tag - NO
    3. Delete a tag - NO

9. Tasks
    1. Create a task - NO
    2. Get task details - NO
    3. Update a task - NO
    4. Delete a task - NO
    5. Update multiple tasks - NO
    6. Delete multiple tasks - NO

10. Time Entries
    1. Create a time entry - YES
    2. Start a time entry - NO
    3. Stop a time entry - NO
    4. Get time entry details - NO
    5. Get running time entry - NO
    6. Update a time entry - NO
    7. Delete a time entry - NO
    8. Get time entries started in a specific time range - NO
    9. Bulk update time entries tags - NO

11. Users
    1. Get current user data without related - YES
    2. Get current user data with related - NO
    3. Update user data - YES
    3. Update user password - YES
    4. Reset API token - NO
    5. Sign up new user - NO
   
12. Workspace Users
    1. Invite users to workspace - NO
    2. Update workspace user - NO
    3. Delete workspace user - NO
    4. Get workspace users - 

13. Workspaces
    1. Get workspaces - YES
    2. Get single workspace - YES
    3. Update workspace - YES
    4. Get workspace users - NO
    5. Get workspace clients - NO
    6. Get workspace groups - NO
    7. Get workspace projects - YES
    8. Get workspace tasks - NO
    9. Get workspace tags - YES

#### Report API implementation status:

1. Detailed report - YES
2. Project dashboard - NO
3. Summary report - NO
4. Weekly report - NO
