import re

def pangram(s):
    alphabet = "abcdefghijklmnopqrstuvwxyz"
    for char in alphabet:
        if char not in s.lower():
            return False
    return True

if(pangram("The quick brown fox jumps over the lazy dog")):
    print("Is a pangram")
else:
    print("Is not a pangram")
