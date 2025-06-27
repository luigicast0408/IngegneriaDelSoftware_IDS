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

1. Create a 3×3 test image:
    - Left side: dark pixels (e.g., black)
    - Right side: bright pixels (e.g., white)
    - This introduces a clear vertical edge in the center

2. Apply the Sobel filter to the image

3. Retrieve the central pixel (1,1)

4. Verify that the intensity (magnitude) of the central pixel is high, as a strong vertical edge is expected there

5. Retrieve a corner pixel (e.g., 0,0)

6. Verify that the intensity of the corner pixel is low, as no edge is expected in uniform regions

---

### Expected Result

- **Center Pixel (1,1)**: High intensity, indicating a strong edge
- **Corner Pixel (0,0)**: Low intensity, indicating no edge

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

---

## Test Steps

1. **Create a 3×3 test image**:
   - Left side: dark pixels (e.g., black)
   - Right side: bright pixels (e.g., white)
   - This setup introduces a strong diagonal edge from the top-left to the bottom-right.

2. **Apply the Roberts filter** to the image using the `RobertsAlgorithm`.

3. **Retrieve the center pixel (1,1)**:
   - Verify that its intensity is high, indicating a detected edge.

4. **Retrieve a corner pixel (e.g., 0,0)**:
   - Verify that its intensity is low, indicating no significant edge in that uniform area.

---

## Expected Result

- **Center Pixel (1,1)**: High intensity, indicating a strong edge.
- **Corner Pixel (0,0)**: Low intensity, indicating no edge.
