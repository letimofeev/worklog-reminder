package com.senla.worklog.reminder.service;

class WorklogDebtsServiceImplTest {
//    @Mock
//    private JiraWorklogApiClient jiraWorklogApiClient;
//
//    @Mock
//    private EmployeeService employeeService;
//
//    @Mock
//    private WorkerFetcher workerFetcher;
//
//    @InjectMocks
//    private WorklogDebtsServiceImpl worklogDebtsService;
//
//    private static final List<AuthorV3> authors = List.of(
//            new AuthorV3().setName("Lol").setKey("lol_key"),
//            new AuthorV3().setName("Kek").setKey("kek_key"),
//            new AuthorV3().setName("Who").setKey("who_key")
//    );
//
//    private static final List<WorklogV3> worklogs = List.of(
//            new WorklogV3()
//                    .setId(1L)
//                    .setJiraWorklogId(101L)
//                    .setAuthor(authors.get(1))
//                    .setDateStarted(LocalDateTime.of(2023, 2, 6, 0, 0,0))
//                    .setTimeSpentSeconds(3600 * 3L),
//            new WorklogV3()
//                    .setId(2L)
//                    .setJiraWorklogId(102L)
//                    .setAuthor(authors.get(1))
//                    .setDateStarted(LocalDateTime.of(2023, 2, 7, 0, 0,0))
//                    .setTimeSpentSeconds(3600 * 7L),
//            new WorklogV3()
//                    .setId(4L)
//                    .setJiraWorklogId(104L)
//                    .setAuthor(authors.get(1))
//                    .setDateStarted(LocalDateTime.of(2023, 2, 9, 0, 0,0))
//                    .setTimeSpentSeconds(3600 * 8L),
//            new WorklogV3()
//                    .setId(6L)
//                    .setJiraWorklogId(106L)
//                    .setAuthor(authors.get(1))
//                    .setDateStarted(LocalDateTime.of(2023, 2, 10, 0, 0,0))
//                    .setTimeSpentSeconds(3600 * 8L),
//            new WorklogV3()
//                    .setId(5L)
//                    .setJiraWorklogId(105L)
//                    .setAuthor(authors.get(0))
//                    .setDateStarted(LocalDateTime.of(2023, 2, 6, 0, 0,0))
//                    .setTimeSpentSeconds(3600 * 7L),
//            new WorklogV3()
//                    .setId(3L)
//                    .setJiraWorklogId(103L)
//                    .setAuthor(authors.get(0))
//                    .setDateStarted(LocalDateTime.of(2023, 2, 8, 0, 0,0))
//                    .setTimeSpentSeconds(3600 * 8L)
//    );
//
//    private static final List<EmployeeDto> employees = List.of(
//            new EmployeeDto().setJiraKey("lol_key").setFirstName("Lolov"),
//            new EmployeeDto().setJiraKey("kek_key").setFirstName("Kekov"),
//            new EmployeeDto().setJiraKey("who_key").setFirstName("Whoov")
//    );
//
//    private static final long requiredDaySpentSeconds = 3600 * 8L;
//
//    @BeforeEach
//    void setUp() {
//        openMocks(this);
//    }
//
//    @Test
//    void getAllForPeriod_shouldReturnExpectedDebts_whenInputIsDates() {
//        when(workerFetcher.getWorkers()).thenReturn(authors);
//
//        when(employeeService.getEmployeeByJiraKey("lol_key")).thenReturn(Optional.ofNullable(employees.get(0)));
//        when(employeeService.getEmployeeByJiraKey("kek_key")).thenReturn(Optional.ofNullable(employees.get(1)));
//        when(employeeService.getEmployeeByJiraKey("who_key")).thenReturn(Optional.ofNullable(employees.get(2)));
//
//        LocalDate dateFrom = LocalDate.of(2023, 2, 6);
//        LocalDate dateTo = LocalDate.of(2023, 2, 10);
//
//        when(jiraWorklogApiClient.getAllForPeriod(dateFrom, dateTo)).thenReturn(worklogs);
//
//        WorklogDebts actual = worklogDebtsService.getAllForPeriod(dateFrom, dateTo);
//
//        List<DayWorklogDebt> employee0Debts = List.of(
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 6))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds - 3600 * 7L),
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 7))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds),
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 9))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds),
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 10))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds));
//
//        List<DayWorklogDebt> employee1Debts = List.of(
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 6))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds - 3600 * 3L),
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 7))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds - 3600 * 7L),
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 8))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds));
//
//        List<DayWorklogDebt> employee2Debts = List.of(
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 6))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds),
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 7))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds),
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 8))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds),
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 9))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds),
//                new DayWorklogDebt()
//                        .setDate(LocalDate.of(2023, 2, 10))
//                        .setTimeDeptSeconds(requiredDaySpentSeconds));
//
//        assertAll(
//                "Grouped Assertions of WorklogDebts",
//                () -> assertEquals(actual.size(), 3),
//                () -> assertEquals(actual.get(employees.get(0)), employee0Debts),
//                () -> assertEquals(actual.get(employees.get(1)), employee1Debts),
//                () -> assertEquals(actual.get(employees.get(2)), employee2Debts)
//        );
//    }
}
