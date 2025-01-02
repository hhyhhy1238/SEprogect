import requests
from PIL import Image
from io import BytesIO

def display_image(url):

    try:
        # 从URL下载图片数据
        response = requests.get(url)
        
        # 使用PIL库打开图片
        image = Image.open(BytesIO(response.content))
        
        # 显示图片
        image.show()
    
    except requests.exceptions.RequestException as e:
        print(f"错误: 无法下载图片 - {e}")
    except IOError as e:
        print(f"错误: 无法打开图片 - {e}")

# 示例用法
image_url = "localhost:9090/ImageResource/avatars/default.png"
display_image(image_url)