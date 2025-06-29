# Test Cases Documentation

## Test: Sobel Filter

### Description
This test verifies the functionality of the Sobel filter in the `SobelAlgorithm` class. It checks whether the filter correctly processes a simple image and produces the expected output.

### What is the Sobel Algorithm?
The Sobel algorithm is a method for detecting edges by calculating the gradient of image intensity at each pixel. It uses two 3×3 convolution masks to compute the horizontal and vertical gradients of an image.

### Sobel Masks (3×3 Kernels)

#### Horizontal Gradient ($G_x$)

$$
G_x = \begin{bmatrix}
-1 & 0 & +1 \\
-2 & 0 & +2 \\
-1 & 0 & +1 \\
\end{bmatrix}
$$

#### Vertical Gradient ($G_y$)

$$
G_y = \begin{bmatrix}
-1 & -2 & -1 \\
0 & 0 & 0 \\
+1 & +2 & +1 \\
\end{bmatrix}
$$

### Gradient Magnitude

The overall gradient magnitude at each pixel is calculated as:

$$
G = \sqrt{G_x^2 + G_y^2}
$$

---

## Test Steps
- Load a real image from the database using ImageRepository.
- Apply the SobelAlgorithm to the image.
- Identify the pixel with the maximum edge response (intensity).
- Verify that:
   - The maximum point shows high intensity (i.e., a valid edge).
   - A point in a dark region shows low intensity.
   - A point in a bright region shows low intensity.
- Save the processed image to disk as a PNG file for visual inspection.
---

### Expected Result

| Region        | Expected Intensity | Notes                          |
| ------------- | ------------------ | ------------------------------ |
| Edge Region   | High (> 0.5)       | Strong edge should be detected |
| Dark Region   | Low (< 0.2)        | Should remain unaffected       |
| Bright Region | Low (< 0.2)        | Should remain unaffected       |

## Test: Roberts Cross Filter
### Description
This test verifies the correctness of the **Roberts Cross** edge detection filter implemented in the `RobertsAlgorithm` class. It checks whether the filter properly identifies edges in a simple image and produces the expected output.

### What is the Roberts Cross Algorithm?
The Roberts Cross algorithm is an edge detection technique that calculates the gradient of image intensity using two 2×2 convolution masks. It is particularly effective at detecting diagonal edges by highlighting rapid changes in intensity between adjacent pixels.

### Roberts Masks (2×2 Kernels)

#### Horizontal Gradient ($G_x$)

$$
G_x = \begin{bmatrix}
+1 & 0 \\
0 & -1 \\
\end{bmatrix}
$$

#### Vertical Gradient ($G_y$)

$$
G_y = \begin{bmatrix}
0 & +1 \\
-1 & 0 \\
\end{bmatrix}
$$

### Gradient Magnitude

The overall gradient magnitude at each pixel is calculated as:

$$
G = \sqrt{G_x^2 + G_y^2}
$$

### Expected Result
| Region       | Expected Intensity | Notes                                                         |
| ------------ | ---------- | ------------------------------------------------------------- |
| Center Pixel | High (> 0.5)| A strong diagonal edge is expected at the transition region   |
| Corner Pixel | Low (< 0.2)| Should remain dark due to uniform intensity (no edge present) |
---

## Test: Prewitt Filter
This test verifies the behavior of the Prewitt edge detection algorithm when applied to a real image retrieved from the database. It ensures the algorithm identifies edges where there is a sharp transition in pixel intensity.

## What is the Prewitt Algorithm?
The Prewitt operator is a gradient-based edge detector. It approximates the derivatives of the image using two 3×3 convolution masks for detecting horizontal and vertical intensity changes.
###  Prewitt Masks (3×3 Kernels)

### Horizontal Gradient ($G_x$)
$$
G_x = \begin{bmatrix}
  -1 & 0 & +1 \\
  -1 & 0 & +1 \\
  -1 & 0 & +1 \\
  \end{bmatrix}
$$
### Vertical Gradient ($G_y$)
$$
  G_x = \begin{bmatrix}
  -1 & +1 & +1 \\
  0 & 0 & 0 \\
  -1 & -1 & +1 \\
  \end{bmatrix}
$$
--- 
## Test Steps

1. **Retrieve the first image from the database using ImageDAO**
   - Use `ImageDAO` and `ImageRepository` to fetch the list of available `ImageEntity` objects.
   - Select the first image from the list.

2. **Load the image from file and apply the Prewitt algorithm**
   - Use the file path from the selected `ImageEntity` to load the image.
   - Pass the loaded image to the `PrewittAlgorithm#apply()` method to compute the edge-detected result.

3. **Identify the pixel with the maximum grayscale intensity**
   - Scan the resulting image.
   - Find the coordinates `(x, y)` of the pixel with the highest grayscale intensity, this is likely on an edge.

4. **Select surrounding test pixels**
   - Define `darkX = edgeX - 40` and `lightX = edgeX + 40`, ensuring bounds are respected.
   - These points are used to sample areas expected to be uniform (non-edge).

5. **Evaluate pixel intensity values**
   - Get the grayscale intensity at `(edgeX, y)` — this should be **high**.
   - Get the grayscale intensity at `(darkX, y)` and `(lightX, y)`  these should be **low**.

6. **Assert expected outcomes**
   - `assertTrue(intensity > 0.5)` at the edge
   - `assertTrue(intensity < 0.2)` on both sides (dark and light)

7. **Save the result image**
   - Export the processed image using `ImageIO.write()` for visual inspection.
   - Filename: `prewitt_image_test.png`

## Expected Results
| Pixel Region         | Expected Intensity | Reason                                         |
| -------------------- | ------------------ | ---------------------------------------------- |
| **Edge Pixel**       | High (> 0.5)       | Strong gradient should be detected             |
| **Dark Side Pixel**  | Low (< 0.2)        | Uniform area, little or no change in intensity |
| **Light Side Pixel** | Low (< 0.2)        | Same as above, but on the brighter region      |
---
# Persistence test 
## Image Repository Test
These unit tests, written using JUnit 5, verify the full set of CRUD operations on the` ImageRepository` class, which manages ImageEntity objects in the database.

| Test Method                           | Description                                                                         |
| ------------------------------------- | ----------------------------------------------------------------------------------- |
| `getAllImagesReturnsArrayListTest()`  | Checks that `getAllImages()` returns a non-null, non-empty list of images.          |
| `getImageByIdReturnsCorrectImage()`   | Inserts an image and verifies that it can be retrieved by its ID with correct data. |
| `getImageByIdReturnsNullIfNotFound()` | Ensures that an invalid ID returns `null`, confirming graceful failure.             |
| `addImageSuccessfullyInserts()`       | Tests whether a new image is correctly inserted into the repository.                |
| `updateImageSuccessfullyModifies()`   | Verifies that updates to an image are saved and accurately reflected in the data.   |
| `deleteImageRemovesCorrectly()`       | Confirms that a deleted image can no longer be retrieved from the repository.       |

### Purpose 
To ensure that the image repository behaves reliably under typical usage scenarios involving data insertion, retrieval, update, and deletion.

---
## Test of Users CRUD 
This set of tests focuses on verifying the CRUD operations and basic authentication setup handled by the` UserRepository` class, which implements the UserDao interface.

| Test Method                           | Description                                                                                      |
| ------------------------------------- | ------------------------------------------------------------------------------------------------ |
| `getAllUsersReturnsArrayListTest()`   | Verifies that `getAllUsers()` returns a non-null, non-empty `ArrayList` of valid `User` objects. |
| `addUserSuccess()`                    | Inserts a new user and checks that it can be retrieved by email.                                 |
| `getUserByIdReturnsCorrectUser()`     | Verifies that a user retrieved by ID matches the originally inserted one.                        |
| `updateUserChangesDataCorrectly()`    | Updates a user's name and surname and verifies the changes were saved.                           |
| `deleteUser()`                        | Deletes a user and ensures it can no longer be retrieved.                                        |

This class is supported of some mock methods for generate mock data

| Method                | Purpose                                                  |
| --------------------- | -------------------------------------------------------- |
| `createTestUser()`    | Creates a mock `User` object with random username/email. |
| `generateTestEmail()` | Generates a unique test email using `UUID`.              |
| `findUserByEmail()`   | Searches for a user in the list by their email.          |

### Purpose
To validate the complete set of CRUD operations and authentication logic associated with the user data stored in the application's database.

--- 
# Test of Design Pattern Factory Method 
This test class verifies the correctness of the Factory Design Pattern implementation used to instantiate different types of GradientAlgorithm objects based on a string input.

| Test Method                                   | Description                                                                   |
| --------------------------------------------- | ----------------------------------------------------------------------------- |
| `testCreateSobelAlgorithm()`                  | Verifies that the factory returns a `SobelAlgorithm` instance for `sobel`.    |
| `testCreatePrewittAlgorithm()`                | Verifies that the factory returns a `PrewittAlgorithm` instance for `prewitt`. |
| `testCreateRobertsAlgorithm()`                | Verifies that the factory returns a `RobertsAlgorithm` instance for `roberts`. |
| `testCreateUnknownAlgorithmThrowsException()` | Ensures that the factory throws an `IllegalArgumentException` for unsupported types. |

## Purpose
To ensure that GradientFactory.create(String type) returns the correct algorithm implementation (Sobel, Prewitt, or Roberts) and throws a proper exception for unknown types.

--- 
# Test of Design Pattern Observer
These test classes verify the correct implementation and behavior of the Observer Pattern. The pattern ensures that observers are properly notified when the state of a subject changes. Each observer reacts to updates in a specific data structure or UI component.

## `SubjectTest` 
### Description
This test ensures that the Subject class manages its observers correctly. It checks that observers can be registered and removed, and that all registered observers receive notifications when the subject’s state changes.

## `ImageListObserverTest`
### Description 
This test verifies that the `ImageListObserver` responds appropriately when notified of changes to the image list. It ensures that the observer correctly reacts, likely by updating a UI component or logging the change.

## `ProgressBarObserverTest`
### Description 
This class tests the `ProgressBarObserver`, which monitors progress updates. It confirms that the observer reacts correctly when the subject signals progress changes, ensuring the progress bar reflects the current state accurately.

## `UserListObserverTest`
This test checks the behavior of the `UserListObserver` in response to updates related to user data. It verifies that the observer properly reflects changes to the user list, such as updating a display or handling internal logic.

---
# Test of **core** Components 
This section documents unit tests for core components of the application, ensuring their correctness, stability, and expected behavior. These components handle critical logic such as image processing, multithreading, and user session management.
## `ImageProcessorTest.java`
This class tests the image processing functionality provided by the `ImageProcessor`. It ensures that image transformation algorithms are applied correctly and that the output is consistent and valid.

The tests typically involve loading an image, applying a filter or algorithm (such as edge detection), and verifying that the resulting image meets expected criteria in terms of pixel intensity or visual structure

## `UserSessionTest`
This test validates the behavior of the `ThreadTask` class, which likely represents a unit of work to be executed in a separate thread. The purpose is to verify that tasks are launched and executed correctly in parallel or asynchronously.

It ensures proper handling of thread life cycle, task execution without blocking the main thread, and correct completion or reporting of task results.

## `ThreadTaskTest`
This test validates the behavior of the `ThreadTask` class, which likely represents a unit of work to be executed in a separate thread. The purpose is to verify that tasks are launched and executed correctly in parallel or asynchronously.

It ensures proper handling of thread life cycle, task execution without blocking the main thread, and correct completion or reporting of task results.

--- 
## `AuthManagerTest`
This test class verifies the behavior of the `AuthManager`, which handles authentication tasks such as user registration, login, logout, and session tracking. It ensures that users can log in successfully with valid credentials, and that login attempts fail when the password is incorrect. It also checks that duplicate usernames are not allowed during registration, maintaining data consistency.

One of the tests confirms that logging out properly clears the current session, ensuring the user is fully disconnected. Another test appears to be a placeholder for admin login functionality but currently only performs a logout operation without any assertions.

All tests are written using JUnit 5 and are isolated through the use of a fresh AuthManager instance initialized before each test. The code makes use of typical assertion methods to verify that the authentication logic behaves as expected under different scenarios.

---