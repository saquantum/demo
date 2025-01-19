from PIL import Image

# ASCII characters from darkest to lightest
ASCII_CHARS = "qwertyuiopasdfghjklzxcvbnm,./;'\\[]-=<>?:\"|{}_+`~!@#$%^&*()QWERTYUIOPASDFGHJKLZXCVBNM "

def resize_image(image, new_width=100):
    """Resize the image while maintaining the aspect ratio."""
    width, height = image.size
    aspect_ratio = height / width
    new_height = int(new_width * aspect_ratio * 0.55)  # Adjust for font aspect ratio
    return image.resize((new_width, new_height))

def grayscale_image(image):
    """Convert image to grayscale."""
    return image.convert("L")  # "L" mode is for 8-bit grayscale

def pixels_to_ascii(image):
    """Map pixel values to ASCII characters."""
    pixels = image.getdata()  # Get pixel intensity values
    ascii_str = "".join([ASCII_CHARS[pixel // 25] for pixel in pixels])  # Map to ASCII
    return ascii_str

def image_to_ascii(image_path, new_width=100):
    """Convert an image to ASCII art."""
    try:
        # Load and process the image
        image = Image.open(image_path)
        image = resize_image(image, new_width)
        image = grayscale_image(image)
        
        # Convert pixels to ASCII
        ascii_str = pixels_to_ascii(image)
        
        # Format the ASCII string into rows
        ascii_width = image.width
        ascii_rows = [ascii_str[i:i + ascii_width] for i in range(0, len(ascii_str), ascii_width)]
        return "\n".join(ascii_rows)
    except Exception as e:
        return f"An error occurred: {e}"

# Test the function with a local image
image_path = "example.jpg"  # Replace with your local image path
ascii_art = image_to_ascii(image_path, new_width=100)
print(ascii_art)

# Optional: Save the ASCII art to a text file
with open("ascii_art.txt", "w") as f:
    f.write(ascii_art)
