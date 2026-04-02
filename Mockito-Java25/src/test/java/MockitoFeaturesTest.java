import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockitoFeaturesTest {

    @Mock
    private List<String> databaseMock;

    @Spy
    private List<String> spyList = new java.util.ArrayList<>();

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    void shouldReturnStubbedValue() {
        when(databaseMock.get(0)).thenReturn("MockedData");
        assertEquals("MockedData", databaseMock.get(0));
    }

    @Test
    void shouldThrowExceptionWhenMethodIsCalled() {
        doThrow(new RuntimeException()).when(databaseMock).clear();
        assertThrows(RuntimeException.class, () -> databaseMock.clear());
    }

    @Test
    void shouldVerifyMethodInteraction() {
        databaseMock.add("item");
        verify(databaseMock, times(1)).add("item");
        verify(databaseMock, never()).clear();
    }

    @Test
    void shouldCaptureArgument() {
        databaseMock.add("capturedValue");
        verify(databaseMock).add(stringCaptor.capture());
        assertEquals("capturedValue", stringCaptor.getValue());
    }

    @Test
    void shouldUseSpyToTrackRealObject() {
        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        assertEquals(2, spyList.size());

        doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
    }

    @Test
    void shouldMockStaticMethod() {
        try (MockedStatic<SystemUtils> utilities = mockStatic(SystemUtils.class)) {
            utilities.when(SystemUtils::getPlatform).thenReturn("Linux");
            assertEquals("Linux", SystemUtils.getPlatform());
        }
    }
}

class SystemUtils {
    public static String getPlatform() {
        return "Windows";
    }
}