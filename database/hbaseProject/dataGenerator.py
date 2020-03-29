from xml.dom import minidom
from enum import Enum

#  export JAVA_HOME=/usr/lib/jvm/java-7-openjdk

class Column_family(Enum):
    vegi = "vegtable"
    proteins = "proteins"
    other = "other"
    meta = "meta"

def main():
    mydoc = minidom.parse('Food_Display_Table.xml')
    items = mydoc.getElementsByTagName('Food_Display_Row')

    item_list = []
    for item in items:
        item_list.append(get_item_dict(item))

    script = create_script(item_list)

    write_to_file("data-script.txt", script)


def write_to_file(path, content):
    with open(path, "w") as file:
        file.writelines(content)


def create_script(item_list):
    table_name = "foods"

    script_text = f"create '{table_name}'"
    for column_family in Column_family:
        script_text += f", '{column_family.value}'"
    
    script_text += "\n"


    for item in item_list:
        for item_name in item["data"]:
            id = item["meta"]["id"]["data"]
            value = item["data"][item_name]["data"]
            column_family = item["data"][item_name]["column_family"].value

            script_text += f"put '{table_name}','{id}','{column_family}:{item_name}','{value}' \n"

    print(script_text)
    return script_text


def get_item_dict(item):
    return {
        "meta": {
            "id": getValue(item, "Food_Code", None),
        },
        "data": {
            "display_name": getValue(item, "Display_Name", Column_family.meta),
            "portion_default": getValue(item, "Portion_Default", Column_family.meta),
            "portion_amount": getValue(item, "Portion_Amount", Column_family.meta),
            "protion_display_name": getValue(item, "Portion_Display_Name", Column_family.meta),
            "incremnet": getValue(item, "Increment", Column_family.meta),
            "multiplier": getValue(item, "Multiplier", Column_family.meta),
            "grains": getValue(item, "Grains", Column_family.other),
            "whole_grains": getValue(item, "Whole_Grains", Column_family.other),
            "vegetables": getValue(item, "Vegetables", Column_family.vegi),
            "orange_vegetables": getValue(item, "Orange_Vegetables", Column_family.vegi),
            "drkgreen_vegetables": getValue(item, "Drkgreen_Vegetables", Column_family.vegi),
            "starchy_vegetables": getValue(item, "Starchy_vegetables", Column_family.vegi),
            "other_vegetables": getValue(item, "Other_Vegetables", Column_family.vegi),
            "fruits": getValue(item, "Fruits", Column_family.other),
            "milk": getValue(item, "Milk", Column_family.proteins),
            "meats": getValue(item, "Meats", Column_family.proteins),
            "soy": getValue(item, "Soy", Column_family.proteins),
            "drybeans_peas": getValue(item, "Drybeans_Peas", Column_family.vegi),
            "oils": getValue(item, "Oils", Column_family.other),
            "solid_fats": getValue(item, "Solid_Fats", Column_family.other),
            "added_sugar": getValue(item, "Added_Sugars", Column_family.other),
            "alcohol": getValue(item, "Alcohol", Column_family.other),
            "calories": getValue(item, "Calories", Column_family.proteins),
            "saturated_fats": getValue(item, "Saturated_Fats", Column_family.other)
        }
    }


def getValue(item, value_name, column_family):
    node = item.getElementsByTagName(value_name)
    data = node[0].firstChild.nodeValue
    data = data.replace("'", "\\'")

    return {
        "data": data,
        "column_family": column_family
    }


if __name__ == "__main__":
    main()
