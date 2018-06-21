package com.turingweb;

import com.turingweb.chess.Chess;
import com.turingweb.chess.LegalMoves;
import com.turingweb.graphics_utilities.image.Image;
import com.turingweb.graphics_utilities.opengl_utilities.Texture;
import com.turingweb.graphics_utilities.opengl_utilities.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;


public class Main
{



    public static void main(String[] args)
    {
        byte gameState[] = new byte[]{
                6,  5,  4,  2,  3,  4,  5,  6,
                1,  1,  1,  1,  1,  1,  1,  1,
                -1,  0,  0,  0,  0,  0,  0,  0,
                0,  0,  0,  0,  0,  0,  0,  0,
                0,  0,  0,  0,  0,  0,  0,  0,
                0,  0,  0,  0,  0,  0,  0,  0,
                0, -1, -1, -1, -1, -1, -1, -1,
                -6, -5, -4, -2, -3, -4, -5, -6, 1
        };

        Object[] legalMoveArray = new Object[32];

        LegalMoves legalMoves = new LegalMoves();
        legalMoveArray = legalMoves.getLegalMovesForGameState(gameState);

        for(int i = 0; i < legalMoveArray.length; i++)
        {
            System.out.println(i + " " + legalMoveArray[i]);
        }


//        Chess chess = new Chess();
//
//        chess.start();
    }
}







































//import org.lwjgl.*;
//import org.lwjgl.glfw.*;
//import org.lwjgl.opengl.*;
//
//import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.opengl.GL11.*;
//import static org.lwjgl.system.MemoryUtil.*;
//
//public class Main {
//
//    // We need to strongly reference callback instances.
//    private GLFWErrorCallback errorCallback;
//    private GLFWKeyCallback   keyCallback;
//
//    // The window handle
//    private long window;
//
//    public void run() {
//        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
//
//        try {
//            init();
//            loop();
//
//            // Release window and window callbacks
//            glfwDestroyWindow(window);
//            keyCallback.release();
//        } finally {
//            // Terminate GLFW and release the GLFWErrorCallback
//            glfwTerminate();
//            errorCallback.release();
//        }
//    }
//
//    private void init() {
//        // Setup an error callback. The default implementation
//        // will print the error message in System.err.
//        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
//
//        // Initialize GLFW. Most GLFW functions will not work before doing this.
//        if ( glfwInit() != GLFW_TRUE )
//            throw new IllegalStateException("Unable to initialize GLFW");
//
//        // Configure our window
//        glfwDefaultWindowHints(); // optional, the current window hints are already the default
//        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
//        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
//
//        int WIDTH = 800;
//        int HEIGHT = 600;
//
//
//
//        // Get the resolution of the primary monitor
//        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//        // Create the window
//        window = glfwCreateWindow(vidmode.width(), HEIGHT, "Hello World!", NULL, NULL);
//        if ( window == NULL )
//            throw new RuntimeException("Failed to create the GLFW window");
//
//        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
//        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
//            @Override
//            public void invoke(long window, int key, int scancode, int action, int mods) {
//                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
//                    glfwSetWindowShouldClose(window, GLFW_TRUE); // We will detect this in our rendering loop
//            }
//        });
//        // Center our window
//        glfwSetWindowPos(
//                window,
//                (vidmode.width() - WIDTH) / 2,
//                (vidmode.height() - HEIGHT) / 2
//        );
//
//        // Make the OpenGL context current
//        glfwMakeContextCurrent(window);
//        // Enable v-sync
//        glfwSwapInterval(1);
//
//        // Make the window visible
//        glfwShowWindow(window);
//    }
//
//    private void loop() {
//        // This line is critical for LWJGL's interoperation with GLFW's
//        // OpenGL context, or any context that is managed externally.
//        // LWJGL detects the context that is current in the current thread,
//        // creates the GLCapabilities instance and makes the OpenGL
//        // bindings available for use.
//        GL.createCapabilities();
//
//        // Set the clear color
//        glClearColor(1.0f, 0.98f, 0.98f, 0.0f);
//
//        // Run the rendering loop until the user has attempted to close
//        // the window or has pressed the ESCAPE key.
//        while ( glfwWindowShouldClose(window) == GLFW_FALSE ) {
//            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
//
//            glfwSwapBuffers(window); // swap the color buffers
//
//            // Poll for window events. The key callback above will only be
//            // invoked during this call.
//            glfwPollEvents();
//        }
//    }
//
//    public static void main(String[] args) {
//        new Main().run();
//    }
//
//}















































//    public static void main(String[] arguments) throws Exception
//    {
//        LargestBlob largestBlob = new LargestBlob();
//        System.out.println(largestBlob.getLargestBlob(new BufferedReader(new FileReader("medium.txt"))));
//        try
//        {
//            //TODO catch file format not recognized
//            ConvolutionImage convolutedImage = new ConvolutionImage(ImageIO.read(new File(arguments[0])));
//
//            try
//            {
//                File outputFile = new File("blur2_"+arguments[0]);
//
//                ImageIO.write(convolutedImage.boxBlur(100), "jpg", outputFile);
//                System.out.println("saved file as "+ "blur2_"+arguments[0]);
//            }
//            catch (IOException e)
//            {
//                System.err.println(e.getMessage());
//            }
//        }
//        catch (IOException exception)
//        {
//            System.err.println(exception.getMessage());
//        }

