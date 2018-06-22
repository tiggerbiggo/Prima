![tomatocado made this](https://cdn.discordapp.com/attachments/272761734820003841/450972471076716545/primaplay.png)

# Prima Play
A re-written version of my image generation library [Prima](https://github.com/tiggerbiggo/Prima), with extra focus on modularity and ease of use.

## How it Works

### Basic concept
An image held in a computer is made up of *pixels*. Each pixel holds a Red, Green, and Blue value. The concept of Prima is similar, except instead of holding Red, Green and Blue values, while an image is being generated in Prima each pixel stores a **Vector**. A Vector is a mathematical description of direction and magnitude, and is expressed as coordinates, e.g *(0, 1)* or *(-3, 5)*. 

### Why this is useful
Since Prima stores each pixel as a Vector, mathematical functions can be applied to these values to transform them. These functions can be, for example, trigonometric functions, complex plane transformations, or any other computable transformation that can be conceived. This allows for extreme flexibility and modularity, to create unique animated and static textures.

### Contributing
Prima Play is currently in its infancy, however the only thing that has changed from the original Prima library is the mechanism by which the components interact. The majority of the code for transforming the images will be identical. Therefore, I have written a [CONTRIBUTING.MD](https://github.com/tiggerbiggo/PrimaPlay/blob/master/CONTRIBUTING.md) file and added a few issues to the Prima Play github. If you want to contribute I am very happy to assist with the understanding of the program, and hopefully you will have ideas I have not been able to come up with. If you have questions or want to join the project, please join my Discord server at http://discord.gg/s76zUPR . I hope to see you there :)
