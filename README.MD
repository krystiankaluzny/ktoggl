## KToggl

KToggl is Kotlin client for the [Toggl API v8](https://github.com/toggl/toggl_api_docs/blob/master/toggl_api.md) 
and the [Toggl Reports API v2](https://github.com/toggl/toggl_api_docs/blob/master/reports.md)

Project is under heavy development and its API (packages, classes, methods names) may change without warning.


### Skipped endpoints

Some Toggl API endpoints was skipped in current implementation because of its low importance (based on my opinion).
Eventually all the endpoints should find itself in KToggl.

If you think that something should be added faster, feel free to create an issue.

#### List of skipped endpoints:
1. Reset API token
2. Sign up new user
3. Invite users to workspace
4. Update workspace user
5. Delete workspace user
6. Get workspace users
7. Get workspace clients
8. Get workspace groups
9. Get workspace tasks