package minechem.utils;

public class SessionVars
{

    private static Class<?> openedTab;

    public static Class<?> getOpenedTab()
    {
        return openedTab;
    }

    public static void setOpenedTab(Class<?> tabClass)
    {
        openedTab = tabClass;
    }
}
