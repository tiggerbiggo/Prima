# Prima Play
A re-written version of my image generation library [Prima](https://github.com/tiggerbiggo/Prima), with extra focus on modularity and ease of use.

## How it Works

### Basic concept
An image held in a computer is made up of *pixels*. Each pixel holds a Red, Green, and Blue value. The concept of Prima is similar, except instead of holding Red, Green and Blue values, while an image is being generated in Prima each pixel stores a **Vector**. A Vector is a mathematical description of direction and magnitude, and is expressed as coordinates, e.g *(0, 1)* or *(-3, 5)*. 

### Why this is useful
Since Prima stores each pixel as a Vector, mathematical functions can be applied to these values to transform them. These functions can be, for example, trigonometric functions, complex plane transformations, or any other computable transformation that can be conceived. This allows for extreme flexibility and modularity, to create unique animated and static textures.

### Rendering an image
Numbers can be transformed, but raw values aren't normally pretty. Prima has a means of rendering these numbers, and it is simply a map from number to colour. Here's an example:

Lets say we have a pixel to render. The value of the pixel is **(0.5, 1)**. The first step is to turn the 2D vector into a scalar value, and this can be done in various ways. The simplest way is to just add the values together, so we now have a scalar value of **1.5**. To choose what colours we want to work with, we can define a **Gradient**. A gradient defines a colour transition given a number, and the simplest one just takes 2 colours and interpolates between the 2 colours in the range 0 - 1 to give a smooth gradient transition. Since our value is not in the range 0 - 1, we just modulo the value with 1 to get **0.5**. The program then works out the colour that lies at the right place on the gradient given our **0.5**, and we have our colour. Finally, we set the correct pixel in the final BufferedImage object to the colour we calculated.

There are various ways of calculating these gradients, but similar to the idea of the rest of the program, rendering is simply a function that takes a vector and gives back a colour, so anything that does this can be used to generate an image.

### Contributing
Prima Play is currently in its infancy, however the only thing that has changed from the original Prima library is the mechanism by which the components interact. The majority of the code for transforming the images will be identical. Therefore, I have written a contribution md file and added a few issues to the Prima Play github. If you want to contribute I am very happy to assist with the understanding of the program, and hopefully you will have ideas I have not been able to come up with.
