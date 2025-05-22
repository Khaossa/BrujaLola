# BrujaLola
App Android de adivinación

## Gemini API Key Setup

To use the features powered by the Gemini API, you need to provide your own API key. Follow these steps:

1.  **Locate or Create `local.properties`:**
    *   This file should be in the root directory of the project (the same directory that contains this `README.md` file and the `app/` directory).
    *   If it doesn't exist, create a new file named `local.properties` in the root project directory.

2.  **Add Your API Key:**
    *   Open the `local.properties` file.
    *   Add the following line, replacing `YOUR_API_KEY_HERE` with your actual Gemini API key:
        ```properties
        GEMINI_API_KEY=YOUR_API_KEY_HERE
        ```

3.  **Important Notes:**
    *   The `local.properties` file is intentionally excluded from version control (Git) by default (it should be listed in the `.gitignore` file). This is to ensure your API key remains private and is not accidentally committed to the repository.
    *   Do not share your API key publicly.
    *   The application will read this key at build time to access the Gemini API.

After adding the key, ensure your project is synced with Gradle.
