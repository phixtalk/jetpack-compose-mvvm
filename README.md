## About jetpack-compose-mvvm-clean-arch

This project is a simple 2 page Android Application built using 
MVVM and Clean Architecture pattern, with android Jetpack Libraries 
and Jetpack Compose for User Interface Design.
Some of the libraries / patterns used includes:

- MVVM
- Jetpack Compose User Interface
- Load Images in Composables with CoilImage
- Pagination
- Offline Search
- Error Handling in Composables
- Compose Navigation with data transfer
- Networking with Retrofit & Gson
- Dependency Injection with Hilt
- Clean Architecture Pattern

### `Clean Architecture Folder Structure`

- Core/Business Module - Non Android related layers
    1. Domain (Core Business Models)
    2. Data (Local/Cache, Network/DTO): Abstraction | Implementation
    3. Interactors (use cases)
- Framework Module
    1. DataSource
    2. Presentation

- Other Directories
    - DI
    - Utils


References: Special thanks to Phillip Lackner, Mitch Tabian
