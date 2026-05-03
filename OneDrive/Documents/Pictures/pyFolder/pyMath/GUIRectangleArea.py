import tkinter as tk

'''

TODO: EXPAND THE PROJECT SO THAT IT INCLUDES MULTIPLE OPERATIONS THAT ARE WELL KNOWN 

TODO: 1. Input type of calculation (maybe a dropdown menu of different calculations instead of a command prompt) 

TODO: 2. A predeveloped function for each operation in the book (some take different parameters) 

TODO: 3. Just output the result in each (not that difficult, just reconfig each of the answer labels to print the answer 
on the GUI 
''' 

window = tk.Tk()
window.title("Area Calculator (RECTANGLES)")
window.geometry("300x300")

label = tk.Label(window, text="Enter the Length") 

label.pack()

entry_length = tk.Entry(window)
entry_length.pack()



label2 = tk.Label(window, text="Enter the Width")
label2.pack()

entry_width = tk.Entry(window)
entry_width.pack()

def rectangle_calculation():
    length = entry_length.get()
    width = entry_width.get()

    area = float(length) * float(width)
    label.config(text=f"Your Area is: {area} Units Squared")

button = tk.Button(window, text="Calculate", command=rectangle_calculation)
button.pack()

window.mainloop()




