cmake_minimum_required(VERSION 3.4.1)

add_library (myNativeLib SHARED src/main/cpp/interface.cpp)


target_include_directories(myNativeLib PUBLIC
        ${CMAKE_SOURCE_DIR}/artifacts/ChickenHook/${CMAKE_LIBRARY_ARCHITECTURE}/include/
        )

target_link_libraries(myNativeLib
        # add chickenhook here
        ${CMAKE_SOURCE_DIR}/artifacts/ChickenHook/${CMAKE_LIBRARY_ARCHITECTURE}/lib/libChickenHook.a
        log
        )