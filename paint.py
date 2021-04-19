import PIL.Image
from PIL import ImageGrab
import win32gui
import tkinter as tk

class Paint(object):

    DEFAULT_PEN_SIZE = 5.0
    DEFAULT_COLOR = 'black'
    SAVED_IMAGE_PATH = 'test.png'

    def __init__(self):
        self.root = tk.Tk()

        self.pen_button = tk.Button(self.root, text='pen', command=self.use_pen)
        self.pen_button.grid(row=0, column=0)

        self.eraser_button = tk.Button(self.root, text='eraser', command=self.use_eraser)
        self.eraser_button.grid(row=0, column=1)

        self.choose_size_button = tk.Scale(self.root, from_=15, to=20, orient=tk.HORIZONTAL)
        self.choose_size_button.grid(row=0, column=2)
        
        self.save_button = tk.Button(self.root, text='done', command=self.save_as_png)
        self.save_button.grid(row=0, column=3)

        self.c = tk.Canvas(self.root, bg='white', width=300, height=300)
        self.c.grid(row=1, columnspan=5)

        self.setup()
        self.root.mainloop()

    def setup(self):
        self.old_x = None
        self.old_y = None
        self.line_width = self.choose_size_button.get()
        self.color = self.DEFAULT_COLOR
        self.eraser_on = False
        self.active_button = self.pen_button
        self.c.bind('<B1-Motion>', self.paint)
        self.c.bind('<ButtonRelease-1>', self.reset)

    def use_pen(self):
        self.activate_button(self.pen_button)

    def use_eraser(self):
        self.activate_button(self.eraser_button, eraser_mode=True)

    def activate_button(self, some_button, eraser_mode=False):
        self.active_button.config(relief=tk.RAISED)
        some_button.config(relief=tk.SUNKEN)
        self.active_button = some_button
        self.eraser_on = eraser_mode

    def paint(self, event):
        self.line_width = self.choose_size_button.get()
        paint_color = 'white' if self.eraser_on else self.color
        if self.old_x and self.old_y:
            self.c.create_line(self.old_x, self.old_y, event.x, event.y,
                               width=self.line_width, fill=paint_color,
                               capstyle=tk.ROUND, smooth=True, splinesteps=36)
        self.old_x = event.x
        self.old_y = event.y

    def reset(self, event):
        self.old_x, self.old_y = None, None
    
    def save_as_png(self):
        handle = self.c.winfo_id()
        portion = win32gui.GetWindowRect(handle)
        img = ImageGrab.grab(portion)
        img.save(self.SAVED_IMAGE_PATH, format='png')
        
    def get_img_path(self):
        return self.SAVED_IMAGE_PATH

p= Paint()
print(p.get_img_path())